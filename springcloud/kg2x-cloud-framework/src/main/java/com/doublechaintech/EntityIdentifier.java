package com.doublechaintech;

public class EntityIdentifier {
  private String type;
  private String id;

  public EntityIdentifier() {}

  public EntityIdentifier(String pType, String pId) {
    type = pType;
    id = pId;
  }

  public String getType() {
    return type;
  }

  public void setType(String pType) {
    type = pType;
  }

  public String getId() {
    return id;
  }

  public void setId(String pId) {
    id = pId;
  }
}
