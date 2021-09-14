package com.doublechain.bank.ordertype;

import com.doublechain.bank.platform.Platform;
import com.doublechaintech.BaseEntity;
import com.doublechaintech.EntityHolder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("order_type_data")
public class OrderType extends BaseEntity {
  public static final String HUGE = "huge";
  public static final String SMALL = "small";

  public static final String ID_PROPERTY = "id";
  public static final String NAME_PROPERTY = "name";
  public static final String CODE_PROPERTY = "code";
  public static final String PLATFORM_RAW_PROPERTY = "platformId";
  public static final String PLATFORM_PROPERTY = "platform";
  public static final String VERSION_PROPERTY = "version";
  public static final String DELETED_PROPERTY = "deleted";

  @Size(min = 0, max = 100)
  private String name;

  @Size(min = 0, max = 100)
  private String code;

  @Transient private Platform platform;

  @JsonIgnore
  @Column("platform")
  private String platformId;

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public OrderType updateName(String name) {
    setName(name);
    return this;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public OrderType updateCode(String code) {
    setCode(code);
    return this;
  }

  public Platform getPlatform() {
    return this.platform;
  }

  public void setPlatform(Platform platform) {
    this.platform = platform;
  }

  public OrderType updatePlatform(Platform platform) {
    setPlatform(platform);
    return this;
  }

  public String getPlatformId() {
    return platformId;
  }

  public void setPlatformId(String platformId) {
    this.platformId = platformId;
  }

  public OrderType updatePlatformId(String platformId) {
    setPlatformId(platformId);
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
      platform.addOrderType(this);
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
    return children;
  }
}
