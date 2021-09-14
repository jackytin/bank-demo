package com.doublechain.bff.filter;

import com.doublechain.bff.util.BffUtil;
import com.doublechaintech.iamservice.Keys;
import com.doublechaintech.util.Util;
import com.doublechain.bff.authenticationtoken.Kg2xAuthenticationToken;
import com.doublechaintech.usercontext.UserContext;
import com.doublechaintech.usercontext.UserContextImpl;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Kg2xBaseJwtTokenFilter extends BasicAuthenticationFilter {

  @Autowired UserContext userContext;

  public Kg2xBaseJwtTokenFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  public Kg2xBaseJwtTokenFilter(
      AuthenticationManager authenticationManager,
      AuthenticationEntryPoint authenticationEntryPoint) {
    super(authenticationManager, authenticationEntryPoint);
  }

  /*
   * jwtToken filter 要做的事情
   *
   * 1. 先取出jwt token
   *      先尝试从header中取出; 没有的话尝试从cookie中取出;
   * 2. 如果没有取到 jwt token, 就结束;
   *    如果取到了, 就讲 jwtToken中解出的两个关键值: kid 和 userId 传给IAM service
   * 3. IAM service 拿到 jwtToken中来的kid和userId, 验证其是否之前登录过的用户(用Kid做key, 用 userId做校验,比较是否匹配)
   *      如果不匹配, 返回失败;
   *      如果匹配,返回一个用户登录的上下文信息, 主要包括 userApp:{secUser, ctx, app}
   * 4. 如果匹配失败, 本filter set一个 anonymousAuthenticationToken
   *    如果匹配, 本filter set 一个 doublechainAuthenticationToken, 里面有userApp的信息
   * 5. 然后后面 userContextResolver里把userApp从token里设置到userContext里
   */

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    Util.ding(request.getRequestURI());
    doSelfJob(request, response, chain);
    chain.doFilter(request, response);
  }

  private void doSelfJob(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    String jwtTokenStr = tryToGetJwtToken(request);
    if (jwtTokenStr == null) {
      return;
    }
    Jwt jwt = BffUtil.parseJwtToken(jwtTokenStr);
    if (jwt == null) {
      return;
    }

    Kg2xAuthenticationToken authToken = getKg2xAuthenticationToken(jwt);
    authToken.setDetails(BffUtil.getRemoteIp(request));
    UserContextImpl ctx = userContext.me();
    ctx.setJwtToken(jwtTokenStr);
    ctx.setId(authToken.getUserId());
    ctx.setUserAppId(authToken.getUserAppId());
    ctx.setDetails(authToken.getDetails());
    SecurityContextHolder.getContext().setAuthentication(authToken);
  }

  public static Kg2xAuthenticationToken getKg2xAuthenticationToken(Jwt jwt) {
    Util.ding("根据header中的JWT token设置用户信息");
    Util.log(jwt.toString());
    DefaultClaims claims = (DefaultClaims) jwt.getBody();
    String userId = (String) claims.get(Keys.KEY_USER_ID);
    String keyId = claims.getId();
    String userApp = (String) claims.get(Keys.KEY_USER_APP_ID);
    Kg2xAuthenticationToken authToken =
        new Kg2xAuthenticationToken(AuthorityUtils.commaSeparatedStringToAuthorityList("sec_user"));
    authToken.setUserId(userId);
    authToken.setToken(keyId);
    authToken.setUserAppId(userApp);

    return authToken;
  }

  private String tryToGetJwtToken(HttpServletRequest request) {
    String header = Util.getHeaderIgnoreCase(request, Keys.JWT_HEADER);
    if (header != null) {
      Util.ding("Get JWT from header: " + header);
      return header;
    }

    Cookie cookie = Util.getCookieIgnoreCase(request, Keys.JWT_HEADER);
    if (cookie != null) {
      Util.ding("Get JWT from cookie:" + cookie.getValue());
      return cookie.getValue();
    }

    Util.ding("Not found JWT");
    return null;
  }
}
