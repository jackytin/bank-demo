package com.doublechaintech.configuration;

import com.doublechaintech.usercontext.UserContext;
import com.doublechaintech.usercontext.UserContextObjectFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;

public class UserContextInitializer implements ApplicationContextInitializer, PriorityOrdered {
  // TODO: 提出方法. 每个service需要扩展
  public BeanFactoryPostProcessor userContextPostProcessor() {
    return beanFactory -> {
      beanFactory.registerResolvableDependency(UserContext.class, new UserContextObjectFactory());
    };
  }

  @Override
  public void initialize(ConfigurableApplicationContext applicationContext) {
    applicationContext.addBeanFactoryPostProcessor(userContextPostProcessor());
  }

  @Override
  public int getOrder() {
    return Ordered.HIGHEST_PRECEDENCE;
  }
}
