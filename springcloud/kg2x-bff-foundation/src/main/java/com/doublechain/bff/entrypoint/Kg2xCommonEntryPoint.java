package com.doublechain.bff.entrypoint;

import com.doublechaintech.util.Util;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** 触发登录页面 */
public class Kg2xCommonEntryPoint extends LoginUrlAuthenticationEntryPoint {

  protected String loginFormXClass = "you.need.fill.LoginForm";
  /**
   * @param loginFormUrl URL where the login page can be found. Should either be relative to the
   *     web-app context path (include a leading {@code /}) or an absolute URL.
   */
  public Kg2xCommonEntryPoint(String loginFormUrl, String loginFormXClass) {
    super(loginFormUrl);
    this.loginFormXClass = loginFormXClass;
  }

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException, ServletException {
    Util.ding("准备登录");
    response.setHeader("X-Class", loginFormXClass);
    super.commence(request, response, authException);
  }
}
