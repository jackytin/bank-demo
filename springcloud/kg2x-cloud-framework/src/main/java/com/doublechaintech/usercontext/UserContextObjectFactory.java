package com.doublechaintech.usercontext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

public class UserContextObjectFactory implements ObjectFactory<UserContext>, Serializable {

  @Override
  public UserContext getObject() throws BeansException {
    HttpServletRequest request =
        ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    String attributeName = UserContext.class.getSimpleName();
    UserContext userContext = (UserContext) request.getAttribute(attributeName);
    if (userContext != null) {
      return userContext;
    }
    userContext = createUserContext(request);
    request.setAttribute(attributeName, userContext);
    return userContext;
  }

  protected UserContextImpl createUserContext(HttpServletRequest request) {
    return new UserContextImpl(request);
  }
}
