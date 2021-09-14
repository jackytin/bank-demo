package com.doublechaintech.configuration;

import com.doublechaintech.BaseEntity;
import com.doublechaintech.EntityHolder;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mapping.callback.EntityCallback;
import org.springframework.data.mapping.callback.EntityCallbacks;
import org.springframework.data.relational.core.mapping.event.AfterLoadCallback;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;

@Configuration(proxyBeanMethods = false)
public class EntityCallBackAutoConfiguration {

  @Bean
  public EntityCallbacks callBacks(BeanFactory beanFactory) {
    return EntityCallbacks.create(beanFactory);
  }

  @Bean
  public BeforeConvertCallback<BaseEntity> syncToRawBeforeSave() {
    return aggregate -> {
      aggregate.syncToRaw();
      return aggregate;
    };
  }

  @Bean
  public BeforeConvertCallback<BaseEntity> checkEntityBeforeSave() {
    return aggregate -> {
      return aggregate;
    };
  }

  @Bean
  public AfterLoadCallback<BaseEntity> saveRefAfterLoad() {
    return aggregate -> {
      aggregate.syncToRef();
      return aggregate;
    };
  }

  @Bean
  public EnhancedAfterLoad<BaseEntity> enhancedAfterLoad() {
    return (entity, holder) -> {
      entity.syncToRef(holder);
      return entity;
    };
  }

  public interface EnhancedAfterLoad<T> extends EntityCallback<T> {
    T enhance(T entity, EntityHolder holder);
  }
}
