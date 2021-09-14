package com.doublechaintech;

/**
 * 发生了业务错误时, 抛这个异常.
 *
 * <p>此异常不会发邮件给系统的运维人员, 但是会回滚业务
 */
public class ErrorOccurredException extends Kg2xException {
  public ErrorOccurredException() {}

  public ErrorOccurredException(String message) {
    super(message);
  }

  public ErrorOccurredException(String message, Throwable cause) {
    super(message, cause);
  }

  public ErrorOccurredException(Throwable cause) {
    super(cause);
  }

  public ErrorOccurredException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
