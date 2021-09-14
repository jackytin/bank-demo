package com.doublechain.bank.platform;

import com.doublechain.bank.merchant.Merchant;
import com.doublechain.bank.ordertype.OrderType;
import com.doublechaintech.BaseEntity;
import com.doublechaintech.EntityHolder;
import com.doublechaintech.SmartList;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

@Table("platform_data")
public class Platform extends BaseEntity {

  public static final String ID_PROPERTY = "id";
  public static final String NAME_PROPERTY = "name";
  public static final String FOUNDED_PROPERTY = "founded";
  public static final String CONTACT_NUMBER_PROPERTY = "contactNumber";
  public static final String VERSION_PROPERTY = "version";
  public static final String DELETED_PROPERTY = "deleted";

  public static final String MERCHANT_LIST = "merchantList";
  public static final String ORDER_TYPE_LIST = "orderTypeList";

  @Size(min = 0, max = 100)
  private String name;

  @Size(min = 0, max = 100)
  private String founded;

  @Min(0)
  @Max(1000)
  private Long contactNumber;

  @Transient private SmartList<Merchant> merchantList;
  @Transient private SmartList<OrderType> orderTypeList;

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Platform updateName(String name) {
    setName(name);
    return this;
  }

  public String getFounded() {
    return this.founded;
  }

  public void setFounded(String founded) {
    this.founded = founded;
  }

  public Platform updateFounded(String founded) {
    setFounded(founded);
    return this;
  }

  public Long getContactNumber() {
    return this.contactNumber;
  }

  public void setContactNumber(Long contactNumber) {
    this.contactNumber = contactNumber;
  }

  public Platform updateContactNumber(Long contactNumber) {
    setContactNumber(contactNumber);
    return this;
  }

  public SmartList<Merchant> getMerchantList() {
    if (this.merchantList == null) {
      this.merchantList = new SmartList<Merchant>();
      this.merchantList.setName(MERCHANT_LIST);
    }
    return this.merchantList;
  }

  public void setMerchantList(SmartList<Merchant> merchantList) {
    this.merchantList = merchantList;
  }

  public Platform addMerchant(Merchant merchant) {
    if (merchant == null) {
      return this;
    }

    merchant.setPlatform(this);
    getMerchantList().add(merchant);
    return this;
  }

  public SmartList<OrderType> getOrderTypeList() {
    if (this.orderTypeList == null) {
      this.orderTypeList = new SmartList<OrderType>();
      this.orderTypeList.setName(ORDER_TYPE_LIST);
    }
    return this.orderTypeList;
  }

  public void setOrderTypeList(SmartList<OrderType> orderTypeList) {
    this.orderTypeList = orderTypeList;
  }

  public Platform addOrderType(OrderType orderType) {
    if (orderType == null) {
      return this;
    }

    orderType.setPlatform(this);
    getOrderTypeList().add(orderType);
    return this;
  }

  public void syncToRaw() {
    super.syncToRaw();
  }

  public void syncToRef(EntityHolder holder) {
    super.syncToRef(holder);
  }

  public Set<BaseEntity> getParents() {
    Set<BaseEntity> parents = new HashSet<>(super.getParents());
    return parents;
  }

  public Set<BaseEntity> getChildren() {
    Set<BaseEntity> children = new HashSet<>(super.getChildren());

    children.addAll(getMerchantList().getData());
    children.addAll(getOrderTypeList().getData());
    return children;
  }
}
