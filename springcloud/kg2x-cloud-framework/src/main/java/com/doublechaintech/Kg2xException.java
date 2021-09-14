package com.doublechaintech;

public class Kg2xException extends RuntimeException {
  protected Kg2xException() {}

  protected Kg2xException(String message) {
    super(message);
  }

  protected Kg2xException(String message, Throwable cause) {
    super(message, cause);
  }

  protected Kg2xException(Throwable cause) {
    super(cause);
  }

  protected Kg2xException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public static void fatalMessage(String message) {
    throw new FatalMessageException(message);
  }

  public static void fatalMessage(String message, Throwable cause) {
    throw new FatalMessageException(message, cause);
  }

  public static void fatalMessage(Throwable cause) {
    throw new FatalMessageException(cause);
  }

  public static void fatalMessage(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    throw new FatalMessageException(message, cause, enableSuppression, writableStackTrace);
  }

  public static void fatalOccurred(String message) {
    throw new FatalOccurredException(message);
  }

  public static void fatalOccurred(String message, Throwable cause) {
    throw new FatalOccurredException(message, cause);
  }

  public static void fatalOccurred(Throwable cause) {
    throw new FatalOccurredException(cause);
  }

  public static void fatalOccurred(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    throw new FatalOccurredException(message, cause, enableSuppression, writableStackTrace);
  }

  public static void errorMessage(String message) {
    throw new ErrorMessageException(message);
  }

  public static void errorMessage(String message, Throwable cause) {
    throw new ErrorMessageException(message, cause);
  }

  public static void errorMessage(Throwable cause) {
    throw new ErrorMessageException(cause);
  }

  public static void errorMessage(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    throw new ErrorMessageException(message, cause, enableSuppression, writableStackTrace);
  }

  public static void errorOccurred(String message) {
    throw new ErrorOccurredException(message);
  }

  public static void errorOccurred(String message, Throwable cause) {
    throw new ErrorOccurredException(message, cause);
  }

  public static void errorOccurred(Throwable cause) {
    throw new ErrorOccurredException(cause);
  }

  public static void errorOccurred(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    throw new ErrorOccurredException(message, cause, enableSuppression, writableStackTrace);
  }
}
