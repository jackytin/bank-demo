package com.doublechain.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication(scanBasePackages = {"com.doublechain.bank", "com.doublechain.bank*service"})
@EnableDiscoveryClient
@EnableJdbcRepositories
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
