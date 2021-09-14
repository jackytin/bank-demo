package com.doublechaintech.usercontext;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class UserContextImpl implements UserContext {
  private String id;
  private String name;
  private String userAppId;
  private String jwtToken;
  private Object details;

  public UserContextImpl(HttpServletRequest pRequest) {
    //    this.name = pRequest.getParameter("name");
    //    this.id = name.toUpperCase();
  }

  public String getName() {
    return name;
  }

  public void setName(String pName) {
    name = pName;
  }

  @Override
  public String getId() {
    return id;
  }

  public void setId(String pId) {
    id = pId;
  }

  public Date now() {
    return new Date();
  }

  @Override
  public UserContext me() {
    return this;
  }

  public String getUserAppId() {
    return userAppId;
  }

  public void setUserAppId(String userAppId) {
    this.userAppId = userAppId;
  }

  public String getJwtToken() {
    return jwtToken;
  }

  public void setJwtToken(String jwtToken) {
    this.jwtToken = jwtToken;
  }

  public Object getDetails() {
    return details;
  }

  public void setDetails(Object details) {
    this.details = details;
  }
}
