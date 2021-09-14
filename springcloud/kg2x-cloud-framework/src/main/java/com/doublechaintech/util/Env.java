package com.doublechaintech.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

public class Env implements ApplicationContextAware {
  protected static ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  public static boolean isProduction() {
    String[] profiles = applicationContext.getEnvironment().getActiveProfiles();
    return Stream.of(profiles).anyMatch(it -> it.contains("prod"));
  }
}
