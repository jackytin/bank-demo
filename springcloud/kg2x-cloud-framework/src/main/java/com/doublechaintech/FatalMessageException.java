package com.doublechaintech;

/**
 * 发生了严重错误时, 抛这个异常.
 *
 * <p>通常严重错误都是由BUG引发的, 所以此异常会发邮件给系统的运维人员, 但是不会回滚业务
 */
public class FatalMessageException extends Kg2xException {
  private FatalMessageException() {}

  protected FatalMessageException(String message) {
    super(message);
  }

  protected FatalMessageException(String message, Throwable cause) {
    super(message, cause);
  }

  protected FatalMessageException(Throwable cause) {
    super(cause);
  }

  protected FatalMessageException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
