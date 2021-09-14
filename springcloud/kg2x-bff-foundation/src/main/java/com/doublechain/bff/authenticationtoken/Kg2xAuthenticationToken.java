package com.doublechain.bff.authenticationtoken;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.util.Collection;

public class Kg2xAuthenticationToken extends AbstractAuthenticationToken {

  protected String userId;
  protected String token;
  protected String userAppId;

  public Kg2xAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
  }

  @Override
  public Object getCredentials() {
    return token;
  }

  @Override
  public Object getPrincipal() {
    return userId;
  }

  @Override
  public boolean implies(Subject subject) {
    return super.implies(subject);
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getUserAppId() {
    return userAppId;
  }

  public void setUserAppId(String userAppId) {
    this.userAppId = userAppId;
  }
}
