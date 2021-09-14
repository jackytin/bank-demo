package com.doublechaintech.response;

public class Response {

  String message;
  boolean success;
  int code;
  Kg2xSerializable data;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public Kg2xSerializable getData() {
    return data;
  }

  public void setData(Kg2xSerializable data) {
    this.data = data;
  }

  public static Response exception(Exception e) {
    e.printStackTrace();
    Response rst = new Response();
    rst.setCode(-1);
    rst.setData(null);
    rst.setMessage("发生错误" + e.getMessage());
    rst.setSuccess(false);
    return rst;
  }

  public static Response success(Kg2xSerializable data) {
    Response rst = new Response();
    rst.setCode(0);
    rst.setData(data);
    rst.setMessage("SUCCESS");
    rst.setSuccess(true);
    return rst;
  }
}
