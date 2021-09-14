package com.doublechaintech;

public class UpdateRequest {
  private BaseEntity root;
  private EntityAction propagation;

  public UpdateRequest(BaseEntity root, EntityAction propagation) {
    this.root = root;
    this.propagation = propagation;
  }

  public BaseEntity getRoot() {
    return root;
  }

  public void setRoot(BaseEntity pRoot) {
    root = pRoot;
  }

  public EntityAction getPropagation() {
    return propagation;
  }

  public void setPropagation(EntityAction pPropagation) {
    propagation = pPropagation;
  }
}
