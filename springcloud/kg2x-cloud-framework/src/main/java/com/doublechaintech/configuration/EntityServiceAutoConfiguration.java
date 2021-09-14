package com.doublechaintech.configuration;

import com.doublechaintech.service.EntityService;
import com.doublechaintech.service.EntityMetaProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(basePackageClasses = EntityService.class)
public class EntityServiceAutoConfiguration {
  @Bean("entityServiceTemplate")
  @LoadBalanced
  public RestTemplate template(RestTemplateBuilder builder) {
    return builder.build();
  }

  @Bean
  @ConditionalOnMissingBean
  public EntityMetaProvider metaProvider() {
    return new EntityMetaProvider();
  }
}
