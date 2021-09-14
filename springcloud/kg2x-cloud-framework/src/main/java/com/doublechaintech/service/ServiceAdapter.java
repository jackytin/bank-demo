package com.doublechaintech.service;

import com.doublechaintech.BaseEntity;
import com.doublechaintech.EntityAction;
import com.doublechaintech.EntityExecutionContext;
import com.doublechaintech.search.BaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Service("baseService")
public class ServiceAdapter extends BaseService implements EntityService {

  @Value("${spring.application.name}")
  private String serviceName;

  @Autowired
  @Qualifier("entityServiceTemplate")
  protected RestTemplate restTemplate;

  public ServiceAdapter() {}

  @Override
  public <T extends BaseEntity> List<T> query(Object userContext, BaseRequest<T> pRequest) {
    String targetServiceName = serviceName(pRequest);

    if (Objects.equals(serviceName, targetServiceName)) {
      return super.query(userContext, pRequest);
    }

    return queryRemote(pRequest, targetServiceName);
  }

  @Override
  protected <T extends BaseEntity> T executeInternal(
      Object userContext,
      Set<String> handled,
      Map<String, BaseEntity> savedPlainEntities,
      T entity,
      EntityAction defaultAction) {
    String type = entity.getInternalType();
    String targetServiceName = entityMetaProvider.getServiceName(type);

    if (Objects.equals(serviceName, targetServiceName)) {
      return super.executeInternal(userContext, handled, savedPlainEntities, entity, defaultAction);
    }

    if (entity.getId() != null && handled.contains(entity.getFullId())) {
      return entity;
    }

    return executeRemote(
        targetServiceName,
        new EntityExecutionContext(
            userContext, handled, savedPlainEntities, entity, defaultAction));
  }

  public <T extends BaseEntity> T executeRemote(
      String targetServiceName, EntityExecutionContext pEntityExecutionContext) {
    EntityExecutionContext response =
        restTemplate.postForObject(
            String.format("http://%s/entityService/processAction", targetServiceName),
            pEntityExecutionContext,
            EntityExecutionContext.class);

    pEntityExecutionContext.getHandled().addAll(response.getHandled());
    pEntityExecutionContext.getEntity().setId(response.getEntity().getId());
    pEntityExecutionContext.getSavedPlainEntities().putAll(response.getSavedPlainEntities());

    return (T) pEntityExecutionContext.getEntity();
  }

  public <T extends BaseEntity> List<T> queryRemote(
      BaseRequest<T> pRequest, String targetServiceName) {
    ParameterizedTypeReference<List<T>> responseType = new ParameterizedTypeReference<List<T>>() {};
    List<T> ret =
        restTemplate
            .exchange(
                String.format("http://%s/entityService/search", targetServiceName),
                HttpMethod.POST,
                new HttpEntity(pRequest),
                responseType)
            .getBody();
    return ret;
  }

  public String serviceName(BaseRequest pRequest) {
    return entityMetaProvider.getServiceName(pRequest);
  }
}
