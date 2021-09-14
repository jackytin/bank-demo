package com.doublechaintech;

/**
 * 发生了严重错误时, 抛这个异常.
 *
 * <p>通常严重错误都是由BUG引发的, 所以此异常会发邮件给系统的运维人员, 同时会回滚业务
 */
public class FatalOccurredException extends Kg2xException {
  public FatalOccurredException() {}

  public FatalOccurredException(String message) {
    super(message);
  }

  public FatalOccurredException(String message, Throwable cause) {
    super(message, cause);
  }

  public FatalOccurredException(Throwable cause) {
    super(cause);
  }

  public FatalOccurredException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
