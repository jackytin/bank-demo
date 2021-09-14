package com.doublechaintech.service;

import com.doublechaintech.BaseEntity;
import com.doublechaintech.search.BaseRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class EntityMetaProvider {

  private static final Logger logger = LoggerFactory.getLogger(EntityMetaProvider.class);

  @Autowired private BeanFactory beanFactory;

  /** key 为entity type, value为service name */
  public Map<String, String> typeServiceMapping = new HashMap<>();

  /** key 为entity type, value为entity 对应 的entity pojo类型 */
  public Map<String, Class<? extends BaseEntity>> typeClassMapping = new HashMap<>();

  /** key 为entity type, value为entity 对应 的request类型 */
  public Map<String, Class<? extends BaseRequest>> typeRequestMapping = new HashMap<>();

  /** key 为entity type, value为display property的名字,多个名称用","分隔 */
  public Map<String, String> typeDisplayProperties = new HashMap<>();

  public String getServiceName(BaseRequest pRequest) {
    return typeServiceMapping.getOrDefault(pRequest.getInternalType(), pRequest.getInternalType());
  }

  public String getServiceName(String type) {
    return typeServiceMapping.getOrDefault(type, type);
  }

  public void registerTypeService(String requestName, String serviceName) {
    typeServiceMapping.put(requestName, serviceName);
  }

  public void registerTypeRequest(String requestName, Class<? extends BaseRequest> requestType) {
    typeRequestMapping.put(requestName, requestType);
  }

  public void registerTypeClass(String requestName, Class<? extends BaseEntity> pojoType) {
    typeClassMapping.put(requestName, pojoType);
  }

  public <T> BaseRequest<T> entityRequest(String type) {
    Class<? extends BaseRequest> requestType = typeRequestMapping.get(type);
    if (requestType == null) {
      throw new IllegalArgumentException("unknown type:" + type);
    }

    Method newInstance = ReflectionUtils.findMethod(requestType, "newInstance");
    try {
      return (BaseRequest<T>) newInstance.invoke(null);
    } catch (Exception pE) {
      logger.error("error when build the request instance", pE);
      return null;
    }
  }

  public void registerTypeDisplayProperties(String type, String displayPropertyNames) {
    typeDisplayProperties.put(type, displayPropertyNames);
  }

  public String[] getDisplayProperties(String type) {
    String displayProperties = typeDisplayProperties.get(type);
    if (displayProperties == null) {
      return new String[0];
    }
    return displayProperties.split(",");
  }

  public CrudRepository resolveRepository(Class<? extends BaseEntity> typeClass) {
    return (CrudRepository)
        beanFactory.getBean(ClassUtils.resolveClassName(typeClass.getName() + "Repository", null));
  }
}
