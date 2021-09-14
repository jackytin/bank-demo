package com.doublechaintech.iamservice;

/**
 * 登录通道.
 *
 * <p>登录通道表达的意思是: 您是通过什么途径登录的?
 *
 * <p>serviceBeanName: 您登录使用了哪个serviceBean. 例如是 resellerService 还是 buyerService serviceMethodName:
 * 您是使用哪个具体的方式登录的, 例如是 loginByAccount 还是 loginByMobile clientName: 您是从哪个端登录的, 例如是 "mobile" 还是 "PDA"
 * 又或者是 "sensor".
 *
 * <p>这些名字会构造成一个 key, 根据这个key,来决定我们使用哪个 handler 来处理登录请求. 所有的值都是自定义的. <xmp> key的格式为
 * <clientName>/<serviceBeanName>/<serviceMethodName> </xmp> 在IamServiceImpl中,
 * 会根据配置的映射关系,来匹配查找对应的handler
 */
public class LoginChannel {
  protected String serviceBeanName;
  protected String serviceMethodName;
  protected String clientName;

  public String getServiceBeanName() {
    return serviceBeanName;
  }

  public void setServiceBeanName(String serviceBeanName) {
    this.serviceBeanName = serviceBeanName;
  }

  public String getServiceMethodName() {
    return serviceMethodName;
  }

  public void setServiceMethodName(String serviceMethodName) {
    this.serviceMethodName = serviceMethodName;
  }

  public String getClientName() {
    return clientName;
  }

  public void setClientName(String clientName) {
    this.clientName = clientName;
  }

  /** 获取当前登陆channel的key */
  public String getKey() {
    return String.format("%s/%s/%s", getClientName(), getServiceBeanName(), getServiceMethodName());
  }

  public static LoginChannel of(String clientName, String beanName, String methodName) {
    LoginChannel rst = new LoginChannel();
    rst.setClientName(clientName);
    rst.setServiceBeanName(beanName);
    rst.setServiceMethodName(methodName);
    return rst;
  }
}
