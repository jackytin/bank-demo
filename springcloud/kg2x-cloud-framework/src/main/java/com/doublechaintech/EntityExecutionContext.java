package com.doublechaintech;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EntityExecutionContext {
  Object userContext;
  Set<String> handled = new HashSet<>();
  Map<String, BaseEntity> savedPlainEntities = new HashMap<>();
  BaseEntity entity;
  EntityAction defaultAction;

  public EntityExecutionContext() {}

  public EntityExecutionContext(
      Object pUserContext,
      Set<String> pHandled,
      Map<String, BaseEntity> savedPlainEntities,
      BaseEntity pEntity,
      EntityAction pDefaultAction) {
    userContext = pUserContext;
    handled = pHandled;
    this.savedPlainEntities = savedPlainEntities;
    entity = pEntity;
    defaultAction = pDefaultAction;
  }

  public Object getUserContext() {
    return userContext;
  }

  public void setUserContext(Object pUserContext) {
    userContext = pUserContext;
  }

  public Set<String> getHandled() {
    return handled;
  }

  public void setHandled(Set<String> pHandled) {
    handled = pHandled;
  }

  public BaseEntity getEntity() {
    return entity;
  }

  public void setEntity(BaseEntity pEntity) {
    entity = pEntity;
  }

  public EntityAction getDefaultAction() {
    return defaultAction;
  }

  public void setDefaultAction(EntityAction pDefaultAction) {
    defaultAction = pDefaultAction;
  }

  public Map<String, BaseEntity> getSavedPlainEntities() {
    return savedPlainEntities;
  }

  public void setSavedPlainEntities(Map<String, BaseEntity> pSavedPlainEntities) {
    savedPlainEntities = pSavedPlainEntities;
  }
}
