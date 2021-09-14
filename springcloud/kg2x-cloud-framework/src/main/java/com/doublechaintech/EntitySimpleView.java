package com.doublechaintech;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class EntitySimpleView {
  @JsonUnwrapped private EntityIdentifier identifier;
  private String displayName;
  private String color;

  public EntitySimpleView() {}

  public EntitySimpleView(BaseEntity pBaseEntity) {
    this.identifier = new EntityIdentifier(pBaseEntity.getInternalType(), pBaseEntity.getId());
    this.displayName = pBaseEntity.getDisplayName();
  }

  public EntityIdentifier getIdentifier() {
    return identifier;
  }

  public void setIdentifier(EntityIdentifier pIdentifier) {
    identifier = pIdentifier;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String pDisplayName) {
    displayName = pDisplayName;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String pColor) {
    color = pColor;
  }
}
