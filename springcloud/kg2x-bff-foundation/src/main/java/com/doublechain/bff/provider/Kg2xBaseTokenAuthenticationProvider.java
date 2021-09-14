package com.doublechain.bff.provider;

import com.doublechaintech.util.Util;
import com.doublechain.bff.authenticationtoken.Kg2xAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

public class Kg2xBaseTokenAuthenticationProvider implements AuthenticationProvider {
  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    Util.ding("被调用");
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      auth.setAuthenticated(true);
      return auth;
    }
    return null;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return Kg2xAuthenticationToken.class.isAssignableFrom(authentication);
  }
}
