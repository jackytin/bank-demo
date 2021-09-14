package com.doublechaintech.iamservice;

import com.doublechaintech.response.Kg2xSerializable;

import java.util.Date;

public class UserLoginInfo implements Kg2xSerializable {
  // secUser ID, 可能为空
  protected String secUserId;
  // 当前选择的userAppId, 可能为空
  protected String userAppId;
  // 目前支持jwtToken, 这个就是jwtToken claim中的id字段值, 不能为空
  protected String tokenId;
  // 下面4个字段是对用户userApp内容的缓存.
  // 从 userAppId完全可以获得这些数据. 缓存只是为了减少数据库访问,提高效率.
  protected String userAppCtxType;
  protected String userAppCtxId;
  protected String userAppAppId;
  protected String userAppAppType;
  // token建立和过期时间. 不能为空
  protected Date createTime;
  protected Date expireTime;
  // 用户登录的渠道
  protected String loginChannelKey;

  public String getSecUserId() {
    return secUserId;
  }

  public void setSecUserId(String secUserId) {
    this.secUserId = secUserId;
  }

  public String getUserAppId() {
    return userAppId;
  }

  public void setUserAppId(String userAppId) {
    this.userAppId = userAppId;
  }

  public String getTokenId() {
    return tokenId;
  }

  public void setTokenId(String tokenId) {
    this.tokenId = tokenId;
  }

  public String getUserAppCtxType() {
    return userAppCtxType;
  }

  public void setUserAppCtxType(String userAppCtxType) {
    this.userAppCtxType = userAppCtxType;
  }

  public String getUserAppCtxId() {
    return userAppCtxId;
  }

  public void setUserAppCtxId(String userAppCtxId) {
    this.userAppCtxId = userAppCtxId;
  }

  public String getUserAppAppId() {
    return userAppAppId;
  }

  public void setUserAppAppId(String userAppAppId) {
    this.userAppAppId = userAppAppId;
  }

  public String getUserAppAppType() {
    return userAppAppType;
  }

  public void setUserAppAppType(String userAppAppType) {
    this.userAppAppType = userAppAppType;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getExpireTime() {
    return expireTime;
  }

  public void setExpireTime(Date expireTime) {
    this.expireTime = expireTime;
  }

  public String getLoginChannelKey() {
    return loginChannelKey;
  }

  public void setLoginChannelKey(String loginChannelKey) {
    this.loginChannelKey = loginChannelKey;
  }
}
