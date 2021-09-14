package com.doublechaintech.iamservice;

import com.doublechaintech.response.Kg2xSerializable;

import java.io.Serializable;
import java.util.List;

/** 登录的结果 */
public class LoginResult implements Kg2xSerializable {
  // 当前选择的user信息. 不能为空
  protected UserLoginInfo currentUserInfo;
  // 该 secUser 所有有效的 userApp 列表. 可能为空, 例如允许自动注册时, 还没有足够的信息来创建对应的userApp
  protected List<UserLoginInfo> candidateUserInfo;
  // 附加信息.
  protected Serializable additionalInfo;
  // 是否通过认证. true 表示此人提供的某些关键新都已经被验证了. 例如 openId 被微信服务器认可了;
  protected boolean authenticated;
  // 登录上下文.
  protected LoginContext loginContext;
  // 是否新用户
  protected boolean newUser;
  // jwtToken
  protected String token;
  // 附件信息, 例如secUser对象本身
  protected Object details;

  //  @JsonProperty("@class")
  //  public Class type=LoginResult.class;

  public UserLoginInfo getCurrentUserInfo() {
    return currentUserInfo;
  }

  public void setCurrentUserInfo(UserLoginInfo currentUserInfo) {
    this.currentUserInfo = currentUserInfo;
  }

  public List<UserLoginInfo> getCandidateUserInfo() {
    return candidateUserInfo;
  }

  public void setCandidateUserInfo(List<UserLoginInfo> candidateUserInfo) {
    this.candidateUserInfo = candidateUserInfo;
  }

  public Serializable getAdditionalInfo() {
    return additionalInfo;
  }

  public void setAdditionalInfo(Serializable additionalInfo) {
    this.additionalInfo = additionalInfo;
  }

  public boolean isAuthenticated() {
    return authenticated;
  }

  public void setAuthenticated(boolean authenticated) {
    this.authenticated = authenticated;
  }

  public LoginContext getLoginContext() {
    return loginContext;
  }

  public void setLoginContext(LoginContext loginContext) {
    this.loginContext = loginContext;
  }

  public boolean isNewUser() {
    return newUser;
  }

  public void setNewUser(boolean newUser) {
    this.newUser = newUser;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Object getDetails() {
    return details;
  }

  public void setDetails(Object details) {
    this.details = details;
  }

  public static LoginResult notFound(LoginContext loginContext) {
    LoginResult rst = new LoginResult();
    rst.setLoginContext(loginContext);
    rst.setAuthenticated(false);
    rst.setNewUser(false);
    return rst;
  }

  public static LoginResult authenticationFailed(LoginContext loginContext) {
    LoginResult rst = new LoginResult();
    rst.setLoginContext(loginContext);
    rst.setAuthenticated(false);
    rst.setNewUser(false);
    return rst;
  }

  public static LoginResult success(LoginContext loginContext) {
    LoginResult rst = new LoginResult();
    rst.setLoginContext(loginContext);
    rst.setAuthenticated(true);
    return rst;
  }
}
