package com.doublechaintech.iamservice;

public class LoginContext {
  // 您是通过什么途径打开登录功能的
  protected LoginChannel loginChannel;
  // 您使用什么方式来登录
  protected LoginMethod loginMethod;
  // 您提供的登录数据有哪些
  protected LoginData loginData;
  // 从哪个IP/设备登录进来的. 标识登录设备的一个字符串. 目前只用了IP.
  protected String loginFrom;

  public LoginChannel getLoginChannel() {
    return loginChannel;
  }

  public void setLoginChannel(LoginChannel loginChannel) {
    this.loginChannel = loginChannel;
  }

  public LoginMethod getLoginMethod() {
    return loginMethod;
  }

  public void setLoginMethod(LoginMethod loginMethod) {
    this.loginMethod = loginMethod;
  }

  public LoginData getLoginData() {
    return loginData;
  }

  public void setLoginData(LoginData loginData) {
    this.loginData = loginData;
  }

  public String getKey() {
    return getLoginMethod().name() + "://" + getLoginChannel().getKey();
  }

  public String getLoginFrom() {
    return loginFrom;
  }

  public void setLoginFrom(String loginFrom) {
    this.loginFrom = loginFrom;
  }

  public static LoginContext of(LoginMethod method, LoginChannel channel, LoginData data) {
    LoginContext result = new LoginContext();
    result.setLoginChannel(channel);
    result.setLoginMethod(method);
    result.setLoginData(data);
    return result;
  }
}
