package com.doublechaintech;

/**
 * 业务上有错误消息时, 抛这个异常.
 *
 * <p>此异常不会发邮件给系统的运维人员, 不会回滚业务, 只是向用户展示错误消息
 */
public class ErrorMessageException extends Kg2xException {
  public ErrorMessageException() {}

  protected ErrorMessageException(String message) {
    super(message);
  }

  protected ErrorMessageException(String message, Throwable cause) {
    super(message, cause);
  }

  protected ErrorMessageException(Throwable cause) {
    super(cause);
  }

  protected ErrorMessageException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
