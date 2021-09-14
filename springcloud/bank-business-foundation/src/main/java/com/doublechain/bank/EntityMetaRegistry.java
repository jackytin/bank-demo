package com.doublechain.bank;

import com.doublechaintech.service.EntityMetaProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

@Component
public class EntityMetaRegistry implements InitializingBean {

  @Autowired private EntityMetaProvider meta;

  @Override
  public void afterPropertiesSet() throws Exception {
    registerTypeService();
    registerTypeClass();
    registerTypeRequestClass();
  }

  public void registerTypeService() {
    meta.registerTypeService("Platform", "platform-service");
    meta.registerTypeService("Merchant", "my-merchant-service");
    meta.registerTypeService("MerchantOrder", "my-merchant-service");
    meta.registerTypeService("OrderType", "platform-service");
  }

  public void registerTypeClass() throws Exception {
    meta.registerTypeClass("Platform", resolveClass("com.doublechain.bank.platform.Platform"));
    meta.registerTypeClass("Merchant", resolveClass("com.doublechain.bank.merchant.Merchant"));
    meta.registerTypeClass(
        "MerchantOrder", resolveClass("com.doublechain.bank.merchantorder.MerchantOrder"));
    meta.registerTypeClass("OrderType", resolveClass("com.doublechain.bank.ordertype.OrderType"));
  }

  public void registerTypeRequestClass() throws Exception {
    meta.registerTypeRequest(
        "Platform", resolveClass("com.doublechain.bank.platform.PlatformRequest"));
    meta.registerTypeRequest(
        "Merchant", resolveClass("com.doublechain.bank.merchant.MerchantRequest"));
    meta.registerTypeRequest(
        "MerchantOrder", resolveClass("com.doublechain.bank.merchantorder.MerchantOrderRequest"));
    meta.registerTypeRequest(
        "OrderType", resolveClass("com.doublechain.bank.ordertype.OrderTypeRequest"));
  }

  public void registryTypeDisplayProperties() {
    meta.registerTypeDisplayProperties("Platform", "");
    meta.registerTypeDisplayProperties("Merchant", "");
    meta.registerTypeDisplayProperties("MerchantOrder", "");
    meta.registerTypeDisplayProperties("OrderType", "");
  }

  private Class resolveClass(String className) throws ClassNotFoundException {
    return ClassUtils.forName(className, null);
  }
}
