package com.doublechain.bff.filter;

import com.doublechain.bff.util.BffUtil;
import com.doublechaintech.iamservice.*;
import com.doublechaintech.util.Util;
import com.doublechain.bff.authenticationtoken.Kg2xAuthenticationToken;
import com.doublechain.bff.handler.CustomAuthenticationSuccessHandler;
import com.doublechain.bff.iamservice.IamBasicService;
import com.doublechaintech.Kg2xException;
import com.doublechaintech.response.Response;
import com.doublechaintech.usercontext.UserContext;
import com.doublechaintech.usercontext.UserContextImpl;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class Kg2xBaseClientLoginFilter extends AbstractAuthenticationProcessingFilter {

  @Autowired IamBasicService iamBasicService;

  @Autowired UserContext userContext;

  public Kg2xBaseClientLoginFilter(String defaultFilterProcessesUrl) {
    super(defaultFilterProcessesUrl);
  }

  public Kg2xBaseClientLoginFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
    super(requiresAuthenticationRequestMatcher);
  }

  public Kg2xBaseClientLoginFilter(
      String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
    super(defaultFilterProcessesUrl, authenticationManager);
  }

  public Kg2xBaseClientLoginFilter(
      RequestMatcher requiresAuthenticationRequestMatcher,
      AuthenticationManager authenticationManager) {
    super(requiresAuthenticationRequestMatcher, authenticationManager);
  }

  static boolean success = true;

  public Kg2xBaseClientLoginFilter(
      String defaultFilterProcessesUrl,
      AuthenticationManager authenticationManager,
      CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
    super(defaultFilterProcessesUrl, authenticationManager);
    setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException, IOException, ServletException {
    Util.ding("被调用");
    byte[] body = Util.readBody(request);
    Util.log("got " + new String(body));
    // Map<String, Object> loginParam = Util.fromJson(body,Map.class);

    Map<String, Object> loginParam = null;
    String jwtToken = getAuthTokenByLogin(request, response, loginParam);

    if (jwtToken != null) {
      response.setHeader(Keys.JWT_HEADER, "Bearer " + jwtToken);
      Cookie loginCookie = new Cookie(Keys.JWT_HEADER, jwtToken);
      response.addCookie(loginCookie);
      Jwt jwt = BffUtil.parseJwtToken(jwtToken);
      Kg2xAuthenticationToken authToken = Kg2xBaseJwtTokenFilter.getKg2xAuthenticationToken(jwt);
      Util.ding("Login got token " + authToken);
      return authToken;
    }

    throw new BadCredentialsException("登录失败");
  }

  private String getAuthTokenByLogin(
      HttpServletRequest request, HttpServletResponse response, Map<String, Object> loginParam) {
    UserContextImpl ctx = userContext.me();
    LoginContext loginContext = new LoginContext();
    loginContext.setLoginChannel(LoginChannel.of("test", "platformService", "testLogin"));
    loginContext.setLoginMethod(LoginMethod.PASSWORD);
    LoginData loginData = new LoginData();
    loginData.setLoginId("SU000001");
    loginData.setPassword("admin123");
    loginContext.setLoginData(loginData);
    loginContext.setLoginFrom(BffUtil.getRemoteIp(request));
    Response rst = iamBasicService.loginWith(loginContext, ctx.getJwtToken());
    if (!rst.isSuccess()) {
      Kg2xException.errorMessage(rst.getMessage());
    }
    LoginResult data = (LoginResult) rst.getData();
    Util.ding("IAM服务返回" + data);
    ctx.setId(data.getCurrentUserInfo().getSecUserId());
    ctx.setUserAppId(data.getCurrentUserInfo().getUserAppId());
    ctx.setJwtToken(data.getToken());
    return data.getToken();
  }
}
