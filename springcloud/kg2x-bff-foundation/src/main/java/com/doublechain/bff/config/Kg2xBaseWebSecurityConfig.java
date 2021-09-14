package com.doublechain.bff.config;

import com.doublechaintech.util.Util;
import com.doublechain.bff.entrypoint.Kg2xCommonEntryPoint;
import com.doublechain.bff.filter.Kg2xBaseClientLoginFilter;
import com.doublechain.bff.filter.Kg2xBaseJwtTokenFilter;
import com.doublechain.bff.handler.CustomAuthenticationSuccessHandler;
import com.doublechain.bff.provider.Kg2xBaseTokenAuthenticationProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.PriorityOrdered;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class Kg2xBaseWebSecurityConfig extends WebSecurityConfigurerAdapter
    implements PriorityOrdered {

  @Value("${kg2x.security.loginPageUrl:/clientLogin.html}")
  String loginPageUrl;

  @Value("${kg2x.security.loginProcessingUrl:/clientLogin}")
  String loginProcessingUrl;

  @Value("${kg2x.security.loginSuccessUrl:/}")
  String loginSuccessUrl;

  @Value("${kg2x.security.logoutUrl:/clientLogout}")
  String logoutUrl;

  @Value("${kg2x.security.logoutSuccessUrl:/}")
  String logoutSuccessUrl;

  @Value("${kg2x.security.loginFormXClass:com.doublechain.bff.LoginForm}")
  String loginFormXClass;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(jwtTokenAuthenticationProvider());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    Util.ding("配置登录相关的基础配置" + http);
    http.exceptionHandling()
        .authenticationEntryPoint(entryPointer())
        .and()
        .formLogin()
        .loginPage(loginPageUrl)
        .loginProcessingUrl(loginProcessingUrl)
        .successHandler(successHandler())
        .and()
        .logout()
        .logoutUrl(logoutUrl)
        .logoutSuccessUrl(logoutSuccessUrl)
        .deleteCookies("Authorization")
        .and()
        .addFilter(jwtTokenPickingFilter())
        .addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class)
        .csrf()
        .disable();
  }

  @Bean
  @ConditionalOnMissingBean
  public AuthenticationEntryPoint entryPointer() {
    return new Kg2xCommonEntryPoint(loginPageUrl, loginFormXClass);
  }

  @Bean
  @ConditionalOnMissingBean
  public Kg2xBaseTokenAuthenticationProvider jwtTokenAuthenticationProvider() {
    return new Kg2xBaseTokenAuthenticationProvider();
  }

  @Bean
  @ConditionalOnMissingBean
  public AuthenticationSuccessHandler successHandler() {
    return new CustomAuthenticationSuccessHandler(loginSuccessUrl);
  }

  @Bean
  @ConditionalOnMissingBean
  public Kg2xBaseClientLoginFilter loginFilter() throws Exception {
    return new Kg2xBaseClientLoginFilter(
        loginProcessingUrl,
        authenticationManager(),
        new CustomAuthenticationSuccessHandler(loginSuccessUrl));
  }

  @Bean
  @ConditionalOnMissingBean
  public Kg2xBaseJwtTokenFilter jwtTokenPickingFilter() throws Exception {
    return new Kg2xBaseJwtTokenFilter(authenticationManager());
  }

  @Override
  public int getOrder() {
    return HIGHEST_PRECEDENCE;
  }
}
