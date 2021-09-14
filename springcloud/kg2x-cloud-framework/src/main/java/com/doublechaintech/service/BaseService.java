package com.doublechaintech.service;

import com.doublechaintech.*;
import com.doublechaintech.configuration.EntityCallBackAutoConfiguration;
import com.doublechaintech.search.BaseRequest;
import com.doublechaintech.search.QueryOperator;
import com.doublechaintech.search.SearchCriteria;
import com.doublechaintech.search.SimplePropertyCriteria;
import com.doublechaintech.util.SnowFlake;
import com.doublechaintech.util.TextUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.core.convert.EntityRowMapper;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.mapping.callback.EntityCallbacks;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class BaseService implements EntityService {
  private static final Logger logger = LoggerFactory.getLogger(BaseService.class);

  @Autowired protected JdbcMappingContext jdbcMappingContext;
  @Autowired protected JdbcConverter jdbcConverter;
  @Autowired protected NamedParameterJdbcTemplate jdbcTemplate;
  @Autowired protected EntityCallbacks entityCallbacks;
  @Autowired protected Dialect dialect;
  @Autowired protected EntityMetaProvider entityMetaProvider;

  public <T extends BaseEntity> List<T> query(Object userContext, BaseRequest<T> pRequest) {
    pRequest.setUserContext(userContext);
    String sql = baseSql(pRequest);
    List<SearchCriteria> searchCriteriaList = pRequest.getSearchCriteriaList();
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("jdbcMappingContext", jdbcMappingContext);
    parameters.put("entityClass", getEntityClass(pRequest));
    parameters.put("userContext", userContext);
    parameters.put("baseService", this);
    if (!searchCriteriaList.isEmpty()) {
      SearchCriteria criteria =
          SearchCriteria.and(
              searchCriteriaList.toArray(new SearchCriteria[searchCriteriaList.size()]));
      String condition = criteria.prepareParameterAndSql(parameters);
      if (condition.equalsIgnoreCase("false") || condition.equalsIgnoreCase("0")) {
        logger.debug("搜索条件为false,无结果集");
        return Collections.emptyList();
      } else if (!condition.equalsIgnoreCase("true") && !condition.equalsIgnoreCase("1")) {
        sql = sql + String.format(" where %s ", condition);
      }
    }
    sql = sql + " " + dialect.limit().getLimitOffset(pRequest.getSize(), pRequest.getOffset());
    logger.debug("sql: {}", sql);

    parameters.remove("jdbcMappingContext");
    parameters.remove("entityClass");
    parameters.remove("userContext");
    parameters.remove("baseService");
    try {
      if (logger.isDebugEnabled()) {
        logger.debug("parameters: {}", new ObjectMapper().writeValueAsString(parameters));
      }
    } catch (JsonProcessingException pE) {
    }
    List<T> ret =
        jdbcTemplate.query(
            sql,
            parameters,
            new EntityRowMapper(
                jdbcMappingContext.getPersistentEntity(pRequest.entityClass()), jdbcConverter));

    EntityHolder holder = new EntityHolder();
    for (T t : ret) {
      entityCallbacks.callback(EntityCallBackAutoConfiguration.EnhancedAfterLoad.class, t, holder);
    }
    enhance(ret, pRequest);
    return ret;
  }

  @Override
  public List<EntitySimpleView> querySimpleView(Object userContext, EntityIdentifier... entities) {
    if (entities == null) {
      return Collections.emptyList();
    }
    Map<String, List<EntityIdentifier>> idsByType =
        Arrays.stream(entities)
            .collect(
                Collectors.toMap(
                    e -> e.getType(),
                    e -> {
                      List<EntityIdentifier> identifiers = new ArrayList<>();
                      identifiers.add(e);
                      return identifiers;
                    },
                    (v1, v2) -> {
                      v1.addAll(v2);
                      return v1;
                    }));

    List<BaseRequest> requests = new ArrayList<>();

    // build requests
    for (Map.Entry<String, List<EntityIdentifier>> entry : idsByType.entrySet()) {
      String k = entry.getKey();
      List<EntityIdentifier> v = entry.getValue();
      BaseRequest request = entityMetaProvider.entityRequest(k);
      request.select(entityMetaProvider.getDisplayProperties(k));
      request.doAddSearchCriteria(new SimplePropertyCriteria("id", QueryOperator.IN, v));
      requests.add(request);
    }

    List<CompletableFuture> subs = new ArrayList<>();
    for (BaseRequest request : requests) {
      subs.add(CompletableFuture.supplyAsync(() -> query(userContext, request)));
    }

    CompletableFuture<Void> all =
        CompletableFuture.allOf(subs.toArray(new CompletableFuture[subs.size()]));

    List ret = new ArrayList<>();
    all.thenAccept(
            e -> {
              for (CompletableFuture<List> sub : subs) {
                ret.addAll(sub.join());
              }
            })
        .join();
    return (List<EntitySimpleView>)
        ret.stream()
            .map(
                e -> {
                  BaseEntity baseEntity = ((BaseEntity) e);
                  return new EntitySimpleView(baseEntity);
                })
            .collect(Collectors.toList());
  }

  @Override
  public BaseEntity lookup(Object userContext, EntityIdentifier entity) {
    if (entity == null) {
      return null;
    }
    BaseRequest request = entityMetaProvider.entityRequest(entity.getType());
    request.selectAll();
    request.doAddSearchCriteria(
        new SimplePropertyCriteria("id", QueryOperator.EQUAL, entity.getId()));

    List results = query(userContext, request);
    if (results == null || results.isEmpty()) {
      return null;
    }
    return (BaseEntity) results.get(0);
  }

  public <T extends BaseEntity> void enhance(List<T> list, BaseRequest<T> pRequest) {
    Map<String, BaseRequest> parentSelects = pRequest.getParentSelects();
    parentSelects.forEach(
        (k, v) -> {
          List<BaseEntity> parentsIdOnly =
              list.stream()
                  .map(item -> (BaseEntity) item.propertyOf(k))
                  .filter(n -> n != null)
                  .collect(Collectors.toList());

          Set<String> parentIds =
              parentsIdOnly.stream().map(BaseEntity::getId).collect(Collectors.toSet());
          v.doAddSearchCriteria(new SimplePropertyCriteria("id", QueryOperator.IN, parentIds));
          Map<String, BaseEntity> parentFull =
              (Map<String, BaseEntity>)
                  query(pRequest.getUserContext(), v).stream()
                      .collect(
                          Collectors.toMap(
                              item -> ((BaseEntity) item).getId(), p -> p, (v1, v2) -> v1));
          parentsIdOnly.forEach(
              parent -> {
                parentFull.get(parent.getId()).copyTo(parent);
              });
        });
    Map<String, BaseRequest> childrenSelects = pRequest.getChildrenSelects();
    Map<String, T> map =
        list.stream().collect(Collectors.toMap(BaseEntity::getId, n -> n, (v1, v2) -> v1));
    childrenSelects.forEach(
        (k, v) -> {
          k = k.substring(v.getInternalType().length());
          String[] names = k.split(":");
          String name = names[0];
          String refName = names[1];
          v.select(name);

          Set<String> ids = list.stream().map(BaseEntity::getId).collect(Collectors.toSet());
          v.doAddSearchCriteria(new SimplePropertyCriteria(name, QueryOperator.IN, ids));
          List<BaseEntity> children = query(pRequest.getUserContext(), v);
          for (BaseEntity child : children) {
            BaseEntity baseEntity = (BaseEntity) child.propertyOf(refName);
            T t = map.get(baseEntity.getId());
            Method addChildMethod = findAddChildMethod(t.getClass(), child.getClass(), k);
            try {
              addChildMethod.invoke(t, child);
            } catch (Exception pE) {
              pE.printStackTrace();
            }
          }
        });
  }

  public static Method findAddChildMethod(Class parent, Class child, String property) {
    Method method = ReflectionUtils.findMethod(parent, "add" + child.getSimpleName(), child);
    if (method == null) {
      method =
          ReflectionUtils.findMethod(
              parent,
              "add" + child.getSimpleName() + "As" + TextUtils.capFirstChar(property),
              child);
    }
    return method;
  }

  public <T extends BaseEntity> T update(Object userContext, UpdateRequest request) {
    BaseEntity root = request.getRoot();
    EntityAction propagation = request.getPropagation();
    return (T) process(userContext, root, propagation);
  }

  public <T extends BaseEntity> T save(Object userContext, T entity) {
    entity.setAction(EntityAction.SAVE);
    return update(userContext, new UpdateRequest(entity, EntityAction.SAVE));
  }

  public <T extends BaseEntity> T saveAll(Object userContext, T entity) {
    entity.setAction(EntityAction.SAVE);
    return update(userContext, new UpdateRequest(entity, EntityAction.SAVE_ALL));
  }

  public <T extends BaseEntity> T saveNew(Object userContext, T entity) {
    return update(userContext, new UpdateRequest(entity, EntityAction.SAVE_NEW));
  }

  public <T extends BaseEntity> T updateGraph(Object userContext, T entity) {
    return update(userContext, new UpdateRequest(entity, EntityAction.GRAPH_VISIT));
  }

  public <T extends BaseEntity> T process(
      Object userContext, T entity, EntityAction defaultAction) {
    if (entity == null) {
      return null;
    }
    HashMap<String, BaseEntity> savedPlainEntities = new HashMap<>();
    T ret =
        executeInternal(userContext, new HashSet<>(), savedPlainEntities, entity, defaultAction);
    fillValues(ret, new HashSet<>(), savedPlainEntities);
    return ret;
  }

  private <T extends BaseEntity> void fillValues(
      BaseEntity entity, Set<String> handled, Map<String, BaseEntity> savedPlainEntities) {
    if (entity == null) {
      return;
    }

    if (!handled.add(entity.getFullId())) {
      return;
    }

    BaseEntity newBaseEntity = savedPlainEntities.get(entity.getFullId());
    if (newBaseEntity != null) {
      newBaseEntity.copySelfPropertiesTo(entity);
    }

    Set<BaseEntity> relatedEntities = entity.getRelated();
    for (BaseEntity related : relatedEntities) {
      fillValues(related, handled, savedPlainEntities);
    }
  }

  protected <T extends BaseEntity> T executeInternal(
      Object userContext,
      Set<String> handled,
      Map<String, BaseEntity> savedPlainEntities,
      T entity,
      EntityAction defaultAction) {
    if (entity == null) {
      return null;
    }
    EntityAction action = entity.getAction();
    if (entity.getId() != null && handled.contains(entity.getFullId())) {
      return entity;
    }
    refineEntity(entity);
    handled.add(entity.getFullId());

    Set<BaseEntity> parents = findParents(entity);
    if (parents != null) {
      for (BaseEntity parent : parents) {
        if (!propagationParent(parent, defaultAction)) {
          continue;
        }
        BaseEntity updatedParent =
            executeInternal(userContext, handled, savedPlainEntities, parent, defaultAction);
        parent.setId(updatedParent.getId());
      }
    }

    CrudRepository repository = resolveRepository(entity);

    T thisEntity = entity;
    if (action != null && action.isSave()) {
      thisEntity = (T) repository.save(entity);
      savedPlainEntities.put(thisEntity.getFullId(), thisEntity.myClone());
    } else if (action != null
        && action.isDelete()
        && (entity.getDeleted() == null || !entity.getDeleted())) {
      entity.setDeleted(true);
      thisEntity = (T) repository.save(entity);
      savedPlainEntities.put(thisEntity.getFullId(), thisEntity.myClone());
    }

    Set<BaseEntity> children = findChildren(thisEntity);
    if (children != null) {
      for (BaseEntity child : children) {
        if (!propagationChild(child, defaultAction)) {
          continue;
        }
        BaseEntity updatedChild =
            executeInternal(userContext, handled, savedPlainEntities, child, defaultAction);
        child.setId(updatedChild.getId());
      }
    }
    return thisEntity;
  }

  private boolean propagationChild(BaseEntity child, EntityAction pDefaultAction) {
    if (child.getAction() != null) {
      return true;
    }
    switch (pDefaultAction) {
      case SAVE:
        return false;
      case SAVE_ALL:
        child.setAction(EntityAction.SAVE);
        return true;
      case SAVE_NEW:
        if (child.isNew()) {
          child.setAction(EntityAction.SAVE);
        }
        return true;
      case DELETE:
      case DELETE_ALL:
        child.setAction(EntityAction.DELETE);
        return true;
      case GRAPH_VISIT:
        return true;
    }
    return false;
  }

  private boolean propagationParent(BaseEntity pParent, EntityAction pDefaultAction) {
    if (pParent.getAction() != null) {
      return true;
    }
    switch (pDefaultAction) {
      case SAVE:
        if (pParent.isNew()) {
          pParent.setAction(EntityAction.SAVE);
          return true;
        }
        return false;
      case SAVE_ALL:
        pParent.setAction(EntityAction.SAVE);
        return true;
      case SAVE_NEW:
        if (pParent.isNew()) {
          pParent.setAction(EntityAction.SAVE);
          return true;
        }
        return true;
      case DELETE:
        return false;
      case DELETE_ALL:
        pParent.setAction(EntityAction.DELETE);
        return true;
      case GRAPH_VISIT:
        return true;
    }
    return false;
  }

  private boolean needDelete(BaseEntity pEntity, EntityAction pAction) {
    EntityAction action = pEntity.getAction();
    if (action != null) {
      return action.isDelete();
    }
    return false;
  }

  private boolean needSave(BaseEntity pEntity, EntityAction pAction) {
    EntityAction action = pEntity.getAction();
    if (action != null) {
      return action.isSave();
    }
    return false;
  }

  private <T extends BaseEntity> void refineEntity(T pEntity) {
    if (pEntity.getId() == null) {
      pEntity.setId(SnowFlake.nextSId());
    }
  }

  protected EntityAction calculateAction(
      Object pUserContext, BaseEntity entity, EntityAction defaultAction) {
    if (entity == null) {
      return null;
    }
    EntityAction action = entity.getAction();
    if (entity.getAction() == null) {
      action = defaultAction;
    }

    if (action == null) {
      return null;
    }

    return action;
  }

  public <T extends BaseEntity> T delete(Object userContext, T entity) {
    entity.setAction(EntityAction.DELETE);
    return update(userContext, new UpdateRequest(entity, EntityAction.DELETE));
  }

  public <T extends BaseEntity> T deleteAll(Object userContext, T entity) {
    entity.setAction(EntityAction.DELETE);
    return update(userContext, new UpdateRequest(entity, EntityAction.DELETE_ALL));
  }

  private Set<BaseEntity> findChildren(BaseEntity pEntity) {
    return pEntity.getChildren();
  }

  private Set<BaseEntity> findParents(BaseEntity pEntity) {
    return pEntity.getParents();
  }

  private CrudRepository resolveRepository(BaseEntity pEntity) {
    return entityMetaProvider.resolveRepository(pEntity.getClass());
  }

  private Class getEntityClass(BaseRequest pRequest) {
    return pRequest.entityClass();
  }

  private String baseSql(BaseRequest pRequest) {
    return String.format(
        "select %s from %s",
        pRequest.getSelects().stream()
            .map(s -> getColumnName(pRequest, (String) s))
            .collect(Collectors.joining(",")),
        getTableName(pRequest));
  }

  private String getColumnName(BaseRequest pRequest, String property) {
    Class entityClass = getEntityClass(pRequest);
    return SearchCriteria.getColumnName(jdbcMappingContext, entityClass, property);
  }

  private String getTableName(BaseRequest pRequest) {
    Class entityClass = getEntityClass(pRequest);
    return SearchCriteria.getTableName(jdbcMappingContext, entityClass);
  }
}
