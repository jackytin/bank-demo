package com.doublechaintech.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.concurrent.Callable;

public class Util {
  public static void ding() {
    if (Env.isProduction()) {
      return;
    }
    ding("调用栈如下");
    new Throwable().printStackTrace(System.err);
  }

  public static void ding(String message) {
    Throwable th = new Throwable(message);
    StackTraceElement st = th.getStackTrace()[1];
    String fileName = st.getFileName();
    String methodName = st.getMethodName();
    int line = st.getLineNumber();
    log(String.format("call %s()(%s:%d): %s", methodName, fileName, line, message));
  }

  public static void log(String message) {
    System.err.println("[    DEBUG    ] " + message);
  }

  public static String getHeaderIgnoreCase(HttpServletRequest request, String headerName) {
    Enumeration<String> names = request.getHeaderNames();
    if (names == null) {
      return null;
    }
    while (names.hasMoreElements()) {
      String x = names.nextElement();
      if (x.equalsIgnoreCase(headerName)) {
        return request.getHeader(x);
      }
    }
    return null;
  }

  public static Cookie getCookieIgnoreCase(HttpServletRequest request, String cookieName) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null || cookies.length <= 0) {
      return null;
    }
    for (Cookie cookie : cookies) {
      if (cookie.getName().equalsIgnoreCase(cookieName)) {
        return cookie;
      }
    }
    return null;
  }

  public static <T> T fromJson(byte[] body, Class<T> mapClass) throws IOException {
    ObjectMapper om = gotObjectMapper();
    return om.readValue(body, mapClass);
  }

  private static ObjectMapper gotObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    return mapper;
  }

  public static byte[] readBody(HttpServletRequest request) throws IOException {
    ServletInputStream ins = request.getInputStream();
    byte[] buff = new byte[1024];
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    int n = 0;
    do {
      n = ins.read(buff);
      if (n > 0) {
        bout.write(buff, 0, n);
      }
    } while (n > 0);
    return bout.toByteArray();
  }

  public static <T> T orElse(Callable<T> callable, T elseValue) {
    try {
      return callable.call();
    } catch (Exception e) {
      return elseValue;
    }
  }

  public static <T> T orNull(Callable<T> callable) {
    return orElse(callable, null);
  }

  public static String dump(Object value) {
    try {
      return gotObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(value);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return null;
    }
  }
}
