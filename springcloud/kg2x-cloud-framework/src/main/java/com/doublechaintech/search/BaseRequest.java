package com.doublechaintech.search;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.bean.BeanDesc;
import cn.hutool.core.bean.BeanUtil;
import com.doublechaintech.BaseEntity;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.data.relational.core.mapping.Embedded;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public abstract class BaseRequest<T> {

  // projection
  private Set<String> selects = new LinkedHashSet<>();

  // query criteria
  private List<SearchCriteria> searchCriteriaList = new ArrayList<>();

  // condition
  private Map<String, BaseRequest> parentSelects = new LinkedHashMap<>();

  // enhance
  private Map<String, BaseRequest> childrenSelects = new LinkedHashMap<>();
  private SimpleOrderBy orderBy = new SimpleOrderBy();
  private int offset;
  private int size = 1000;
  private Object userContext;

  public Object getUserContext() {
    return userContext;
  }

  public void setUserContext(Object pUserContext) {
    userContext = pUserContext;
  }

  public abstract String getInternalType();

  public abstract Class<T> entityClass();

  public void doAddSearchCriteria(SearchCriteria pSearchCriteria) {
    searchCriteriaList.add(pSearchCriteria);
  }

  public BaseRequest<T> select(String... names) {
    if (names == null) {
      return this;
    }
    if (selects.isEmpty()) {
      selects.add("id");
    }

    for (String name : names) {
      selects.add(name);
    }
    return this;
  }

  public BaseRequest<T> select(EmbeddedProperty property) {
    if (property == null) {
      return this;
    }

    List<String> propertyNames = getEmbeddedPropertyNames(property);
    return select(propertyNames.toArray(new String[propertyNames.size()]));
  }

  private List<String> getEmbeddedPropertyNames(EmbeddedProperty property) {
    Field field = BeanUtil.getBeanDesc(property.clazz).getField(property.property);
    String prefix = AnnotationUtil.getAnnotationValue(field, Embedded.class, "prefix");
    return getPropertyNames(prefix, field.getType());
  }

  private List<String> getPropertyNames(String pPrefix, Class type) {
    List<BeanDesc.PropDesc> embeddedProperties =
        BeanUtil.getBeanDesc(type).getProps().stream()
            .filter(p -> AnnotationUtil.getAnnotation(p.getFieldClass(), Embedded.class) != null)
            .collect(Collectors.toList());
    List<String> commonProperties =
        BeanUtil.getBeanDesc(type).getProps().stream()
            .filter(p -> AnnotationUtil.getAnnotation(p.getFieldClass(), Embedded.class) == null)
            .map(p -> pPrefix + p.getFieldName())
            .collect(Collectors.toList());

    List<String> ret = new ArrayList<>(commonProperties);
    for (BeanDesc.PropDesc embeddedProperty : embeddedProperties) {
      List<String> propertyNames =
          getEmbeddedPropertyNames(
              new EmbeddedProperty(
                  embeddedProperty.getFieldClass(), embeddedProperty.getFieldName()));
      propertyNames.stream()
          .forEach(
              p -> {
                ret.add(pPrefix + p);
              });
    }
    return ret;
  }

  public BaseRequest<T> selectAll() {
    return this;
  }

  public void selectParent(String name, String refName, BaseRequest request) {
    select(name);
    parentSelects.put(refName, request);
  }

  public void selectChild(String name, String refName, BaseRequest request) {
    childrenSelects.put(request.getInternalType() + name + ":" + refName, request);
  }

  public BaseRequest<T> unselect(String... names) {
    if (names == null) {
      return this;
    }
    for (String name : names) {
      selects.remove(name);
    }
    return this;
  }

  public BaseRequest<T> unselect(EmbeddedProperty property) {
    if (property == null) {
      return this;
    }

    List<String> propertyNames = getEmbeddedPropertyNames(property);
    return unselect(propertyNames.toArray(new String[propertyNames.size()]));
  }

  public void unselectParent(String name, String refName) {
    unselect(name);
    parentSelects.remove(refName);
  }

  public <R extends BaseEntity> void unselectChild(
      String name, String refName, Class<R> childClass) {
    childrenSelects.remove(childClass.getSimpleName() + name + ":" + refName);
  }

  public List<SearchCriteria> getSearchCriteriaList() {
    return searchCriteriaList;
  }

  public void setSearchCriteriaList(List<SearchCriteria> pSearchCriteriaList) {
    searchCriteriaList = pSearchCriteriaList;
  }

  public int getOffset() {
    return offset;
  }

  public void setOffset(int pOffset) {
    offset = pOffset;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int pSize) {
    size = pSize;
  }

  public Set<String> getSelects() {
    return selects;
  }

  public void setSelects(Set<String> pSelects) {
    selects = pSelects;
  }

  public Map<String, BaseRequest> getParentSelects() {
    return parentSelects;
  }

  public void setParentSelects(Map<String, BaseRequest> pParentSelects) {
    parentSelects = pParentSelects;
  }

  public Map<String, BaseRequest> getChildrenSelects() {
    return childrenSelects;
  }

  public void setChildrenSelects(Map<String, BaseRequest> pChildrenSelects) {
    childrenSelects = pChildrenSelects;
  }

  public void addOrderBy(String property, boolean asc) {
    orderBy.addOrderBy(property, asc);
  }

  public SimpleOrderBy getOrderBy() {
    return orderBy;
  }

  public void setOrderBy(SimpleOrderBy pOrderBy) {
    orderBy = pOrderBy;
  }
}
