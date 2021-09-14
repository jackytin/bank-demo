package com.doublechaintech;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EntityHolder {
  private Map<String, BaseEntity> cache = new ConcurrentHashMap<>();

  public <T extends BaseEntity> T getEntity(T entity) {
    if (entity == null) {
      return null;
    }
    if (cache.containsKey(entity.getFullId())) {
      return (T) cache.get(entity.getFullId());
    }
    cache.put(entity.getFullId(), entity);
    return entity;
  }
}
