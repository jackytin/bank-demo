package com.doublechain.bank.merchantorder;

import com.doublechain.bank.merchant.Merchant;
import com.doublechaintech.BaseEntity;
import com.doublechaintech.EntityHolder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Past;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("merchant_order_data")
public class MerchantOrder extends BaseEntity {

  public static final String ID_PROPERTY = "id";
  public static final String TOTAL_AMOUT_PROPERTY = "totalAmout";
  public static final String CREATE_DATE_PROPERTY = "createDate";
  public static final String SELLER_RAW_PROPERTY = "sellerId";
  public static final String SELLER_PROPERTY = "seller";
  public static final String BUYER_RAW_PROPERTY = "buyerId";
  public static final String BUYER_PROPERTY = "buyer";
  public static final String VERSION_PROPERTY = "version";
  public static final String DELETED_PROPERTY = "deleted";

  @DecimalMin("0")
  @DecimalMax("1000")
  private BigDecimal totalAmout;

  @Past private LocalDate createDate;

  @Transient private Merchant seller;

  @JsonIgnore
  @Column("seller")
  private String sellerId;

  @Transient private Merchant buyer;

  @JsonIgnore
  @Column("buyer")
  private String buyerId;

  public BigDecimal getTotalAmout() {
    return this.totalAmout;
  }

  public void setTotalAmout(BigDecimal totalAmout) {
    this.totalAmout = totalAmout;
  }

  public MerchantOrder updateTotalAmout(BigDecimal totalAmout) {
    setTotalAmout(totalAmout);
    return this;
  }

  public LocalDate getCreateDate() {
    return this.createDate;
  }

  public void setCreateDate(LocalDate createDate) {
    this.createDate = createDate;
  }

  public MerchantOrder updateCreateDate(LocalDate createDate) {
    setCreateDate(createDate);
    return this;
  }

  public Merchant getSeller() {
    return this.seller;
  }

  public void setSeller(Merchant seller) {
    this.seller = seller;
  }

  public MerchantOrder updateSeller(Merchant seller) {
    setSeller(seller);
    return this;
  }

  public String getSellerId() {
    return sellerId;
  }

  public void setSellerId(String sellerId) {
    this.sellerId = sellerId;
  }

  public MerchantOrder updateSellerId(String sellerId) {
    setSellerId(sellerId);
    return this;
  }

  public Merchant getBuyer() {
    return this.buyer;
  }

  public void setBuyer(Merchant buyer) {
    this.buyer = buyer;
  }

  public MerchantOrder updateBuyer(Merchant buyer) {
    setBuyer(buyer);
    return this;
  }

  public String getBuyerId() {
    return buyerId;
  }

  public void setBuyerId(String buyerId) {
    this.buyerId = buyerId;
  }

  public MerchantOrder updateBuyerId(String buyerId) {
    setBuyerId(buyerId);
    return this;
  }

  public void syncToRaw() {
    super.syncToRaw();

    syncToRawSeller();
    syncToRawBuyer();
  }

  public void syncToRawSeller() {
    if (getSeller() != null) {
      setSellerId(getSeller().getId());
      return;
    }
    setSellerId(null);
  }

  public void syncToRawBuyer() {
    if (getBuyer() != null) {
      setBuyerId(getBuyer().getId());
      return;
    }
    setBuyerId(null);
  }

  public void syncToRef(EntityHolder holder) {
    super.syncToRef(holder);

    syncToRefSeller(holder);
    syncToRefBuyer(holder);
  }

  public void syncToRefSeller(EntityHolder entity_holder) {
    if (getSellerId() != null) {
      Merchant seller = new Merchant();
      seller.setId(getSellerId());
      seller = entity_holder.getEntity(seller);
      seller.addMerchantOrderAsSeller(this);
      return;
    }
    setSeller(null);
  }

  public void syncToRefBuyer(EntityHolder entity_holder) {
    if (getBuyerId() != null) {
      Merchant buyer = new Merchant();
      buyer.setId(getBuyerId());
      buyer = entity_holder.getEntity(buyer);
      buyer.addMerchantOrderAsBuyer(this);
      return;
    }
    setBuyer(null);
  }

  public Set<BaseEntity> getParents() {
    Set<BaseEntity> parents = new HashSet<>(super.getParents());

    if (getSeller() != null) {
      parents.add(getSeller());
    }
    if (getBuyer() != null) {
      parents.add(getBuyer());
    }
    return parents;
  }

  public Set<BaseEntity> getChildren() {
    Set<BaseEntity> children = new HashSet<>(super.getChildren());
    return children;
  }
}
