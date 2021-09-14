package com.doublechaintech;

import com.doublechaintech.util.SnowFlake;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.Version;

import java.util.*;
import java.util.stream.Collectors;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@uuid")
public class BaseEntity {
  public static final int STUB_ENTITY_VERSION = Integer.MAX_VALUE - 10000;
  @Id private String id;
  @Version private Integer version;
  private Boolean deleted;

  @Transient private EntityAction action;
  @Transient private Map<String, Object> valueMap = new HashMap<>();

  public String getId() {
    return id;
  }

  public void setId(String pId) {
    id = pId;
  }

  public void syncToRaw() {
    if (getId() == null) {
      setId(SnowFlake.nextSId());
    }
  }

  public void syncToRef() {
    syncToRef(new EntityHolder());
  }

  public void syncToRef(EntityHolder pHolder) {}

  public void copyTo(BaseEntity dest) {}

  public Object propertyOf(String property) {
    return new BeanWrapperImpl(this).getPropertyValue(property);
  }

  public String getFullId() {
    return getInternalType() + ":" + getId();
  }

  public String getInternalType() {
    return getClass().getSimpleName();
  }

  public List<KeyValuePair> keyValuePairOf() {
    return new ArrayList<>();
  }

  @JsonAnyGetter
  public Map<String, Object> keyPairs() {
    return keyValuePairOf().stream()
        .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (v1, v2) -> v1));
  }

  protected void appendKeyValuePair(List<KeyValuePair> list, String key, Object value) {
    if (list == null) {
      throw new IllegalArgumentException(
          "createKeyValuePair(List<KeyValuePair> list, String key, Object value): list could not be null");
    }
    if (key == null) {
      throw new IllegalArgumentException(
          "createKeyValuePair(List<KeyValuePair> list, String key, Object value): key could not be null");
    }
    if (value == null) {
      return;
    }
    KeyValuePair pair = new KeyValuePair();
    pair.setKey(key);
    pair.setValue(value);
    list.add(pair);
  }

  public String getDisplayName() {
    return getFullId();
  }

  public EntityAction getAction() {
    return action;
  }

  public void setAction(EntityAction pAction) {
    if (this.action != null) {
      return;
    }
    action = pAction;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getFullId());
  }

  @Override
  public boolean equals(Object obj) {
    if (!Objects.equals(obj.getClass(), this.getClass())) {
      return false;
    }
    return Objects.equals(getFullId(), ((BaseEntity) obj).getFullId());
  }

  public Set<BaseEntity> getParents() {
    return Collections.EMPTY_SET;
  }

  public Set<BaseEntity> getChildren() {
    return Collections.EMPTY_SET;
  }

  public final Set<BaseEntity> getRelated() {
    Set<BaseEntity> related = new HashSet<>(getParents());
    related.addAll(getChildren());
    return related;
  }

  public void copySelfPropertiesTo(BaseEntity dest) {
    if (dest == null) {
      return;
    }
    dest.setId(this.getId());
    dest.setVersion(this.getVersion());
    dest.setDeleted(this.getDeleted());
  }

  public final BaseEntity myClone() {
    try {
      BaseEntity dest = this.getClass().newInstance();
      copySelfPropertiesTo(dest);
      return dest;
    } catch (Exception pE) {
      pE.printStackTrace();
    }
    return null;
  }

  public boolean isNew() {
    return version == null || version == 0;
  }

  public void setVersion(Integer pVersion) {
    version = pVersion;
  }

  public Integer getVersion() {
    return version;
  }

  public Boolean getDeleted() {
    return deleted;
  }

  public void setDeleted(Boolean pDeleted) {
    deleted = pDeleted;
  }

  public BaseEntity markAsDeleted() {
    setAction(EntityAction.DELETE);
    return this;
  }
}
