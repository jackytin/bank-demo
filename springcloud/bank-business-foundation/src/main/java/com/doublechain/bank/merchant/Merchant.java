package com.doublechain.bank.merchant;

import com.doublechain.bank.geolocation.Geolocation;
import com.doublechain.bank.merchantorder.MerchantOrder;
import com.doublechain.bank.platform.Platform;
import com.doublechaintech.BaseEntity;
import com.doublechaintech.EntityHolder;
import com.doublechaintech.SmartList;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Table("merchant_data")
public class Merchant extends BaseEntity {

  public static final String ID_PROPERTY = "id";
  public static final String NAME_PROPERTY = "name";
  public static final String ADMIN_PROPERTY = "admin";
  public static final String ENABLED_PROPERTY = "enabled";
  public static final String PLATFORM_RAW_PROPERTY = "platformId";
  public static final String PLATFORM_PROPERTY = "platform";
  public static final String CLEAR_PASSWD_PROPERTY = "clearPasswd";
  public static final String PASSWD_PROPERTY = "passwd";
  public static final String LOCATION_PROPERTY = "location";
  public static final String VERSION_PROPERTY = "version";
  public static final String DELETED_PROPERTY = "deleted";

  public static final String MERCHANT_ORDER_LIST_AS_SELLER = "merchantOrderListAsSeller";
  public static final String MERCHANT_ORDER_LIST_AS_BUYER = "merchantOrderListAsBuyer";

  @Size(min = 0, max = 100)
  private String name;

  @Size(min = 0, max = 100)
  @Email
  private String admin;

  private Boolean enabled;

  @Transient @NotNull private Platform platform;

  @JsonIgnore
  @Column("platform")
  private String platformId;

  private String passwd;

  @JsonIgnore
  @Transient
  @Size(min = 6, max = 8)
  private String clearPasswd;

  @Embedded.Nullable(prefix = "location_")
  @Valid
  private Geolocation location;

  @Transient private SmartList<MerchantOrder> merchantOrderListAsSeller;
  @Transient private SmartList<MerchantOrder> merchantOrderListAsBuyer;

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Merchant updateName(String name) {
    setName(name);
    return this;
  }

  public String getAdmin() {
    return this.admin;
  }

  public void setAdmin(String admin) {
    this.admin = admin;
  }

  public Merchant updateAdmin(String admin) {
    setAdmin(admin);
    return this;
  }

  public Boolean getEnabled() {
    return this.enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public Merchant updateEnabled(Boolean enabled) {
    setEnabled(enabled);
    return this;
  }

  public Platform getPlatform() {
    return this.platform;
  }

  public void setPlatform(Platform platform) {
    this.platform = platform;
  }

  public Merchant updatePlatform(Platform platform) {
    setPlatform(platform);
    return this;
  }

  public String getPlatformId() {
    return platformId;
  }

  public void setPlatformId(String platformId) {
    this.platformId = platformId;
  }

  public Merchant updatePlatformId(String platformId) {
    setPlatformId(platformId);
    return this;
  }

  public String getPasswd() {
    return this.passwd;
  }

  public void setPasswd(String passwd) {
    this.passwd = passwd;
  }

  public Merchant updatePasswd(String passwd) {
    setPasswd(passwd);
    return this;
  }

  public String getClearPasswd() {
    return this.clearPasswd;
  }

  public void setClearPasswd(String clearPasswd) {
    this.clearPasswd = clearPasswd;
    setPasswd(new BCryptPasswordEncoder().encode(clearPasswd));
  }

  public Merchant updateClearPasswd(String clearPasswd) {
    setClearPasswd(clearPasswd);
    return this;
  }

  public Geolocation getLocation() {
    return this.location;
  }

  public void setLocation(Geolocation location) {
    this.location = location;
  }

  public Merchant updateLocation(Geolocation location) {
    setLocation(location);
    return this;
  }

  public SmartList<MerchantOrder> getMerchantOrderListAsSeller() {
    if (this.merchantOrderListAsSeller == null) {
      this.merchantOrderListAsSeller = new SmartList<MerchantOrder>();
      this.merchantOrderListAsSeller.setName(MERCHANT_ORDER_LIST_AS_SELLER);
    }
    return this.merchantOrderListAsSeller;
  }

  public void setMerchantOrderListAsSeller(SmartList<MerchantOrder> merchantOrderListAsSeller) {
    this.merchantOrderListAsSeller = merchantOrderListAsSeller;
  }

  public Merchant addMerchantOrderAsSeller(MerchantOrder merchantOrderAsSeller) {
    if (merchantOrderAsSeller == null) {
      return this;
    }

    merchantOrderAsSeller.setSeller(this);
    getMerchantOrderListAsSeller().add(merchantOrderAsSeller);
    return this;
  }

  public SmartList<MerchantOrder> getMerchantOrderListAsBuyer() {
    if (this.merchantOrderListAsBuyer == null) {
      this.merchantOrderListAsBuyer = new SmartList<MerchantOrder>();
      this.merchantOrderListAsBuyer.setName(MERCHANT_ORDER_LIST_AS_BUYER);
    }
    return this.merchantOrderListAsBuyer;
  }

  public void setMerchantOrderListAsBuyer(SmartList<MerchantOrder> merchantOrderListAsBuyer) {
    this.merchantOrderListAsBuyer = merchantOrderListAsBuyer;
  }

  public Merchant addMerchantOrderAsBuyer(MerchantOrder merchantOrderAsBuyer) {
    if (merchantOrderAsBuyer == null) {
      return this;
    }

    merchantOrderAsBuyer.setBuyer(this);
    getMerchantOrderListAsBuyer().add(merchantOrderAsBuyer);
    return this;
  }

  public void syncToRaw() {
    super.syncToRaw();

    syncToRawPlatform();
  }

  public void syncToRawPlatform() {
    if (getPlatform() != null) {
      setPlatformId(getPlatform().getId());
      return;
    }
    setPlatformId(null);
  }

  public void syncToRef(EntityHolder holder) {
    super.syncToRef(holder);

    syncToRefPlatform(holder);
  }

  public void syncToRefPlatform(EntityHolder entity_holder) {
    if (getPlatformId() != null) {
      Platform platform = new Platform();
      platform.setId(getPlatformId());
      platform = entity_holder.getEntity(platform);
      platform.addMerchant(this);
      return;
    }
    setPlatform(null);
  }

  public Set<BaseEntity> getParents() {
    Set<BaseEntity> parents = new HashSet<>(super.getParents());

    if (getPlatform() != null) {
      parents.add(getPlatform());
    }
    return parents;
  }

  public Set<BaseEntity> getChildren() {
    Set<BaseEntity> children = new HashSet<>(super.getChildren());

    children.addAll(getMerchantOrderListAsSeller().getData());
    children.addAll(getMerchantOrderListAsBuyer().getData());
    return children;
  }
}
