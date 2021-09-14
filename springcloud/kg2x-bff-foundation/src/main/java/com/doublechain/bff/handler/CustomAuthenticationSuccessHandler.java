package com.doublechain.bff.handler;

import com.doublechaintech.util.Util;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationSuccessHandler
    extends SavedRequestAwareAuthenticationSuccessHandler {
  public CustomAuthenticationSuccessHandler(String defaultUrl) {
    super();
    setDefaultTargetUrl(defaultUrl);
    setAlwaysUseDefaultTargetUrl(false);
  }

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authentication)
      throws IOException, ServletException {
    Util.ding("链中调用");
    super.onAuthenticationSuccess(request, response, chain, authentication);
  }

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    Util.ding("被调用");
    super.onAuthenticationSuccess(request, response, authentication);
  }
}
