package com.doublechain.bank.merchant;

import com.doublechain.bank.merchantorder.MerchantOrder;
import com.doublechain.bank.merchantorder.MerchantOrderRequest;
import com.doublechain.bank.platform.Platform;
import com.doublechain.bank.platform.PlatformRequest;
import com.doublechaintech.search.BaseRequest;
import com.doublechaintech.search.EmbeddedProperty;
import com.doublechaintech.search.IDRefine;
import com.doublechaintech.search.QueryOperator;
import com.doublechaintech.search.RefinedIdInCriteria;
import com.doublechaintech.search.SearchCriteria;
import com.doublechaintech.search.SimplePropertyCriteria;
import java.util.stream.Collectors;

public class MerchantRequest extends BaseRequest<Merchant> {
  public static MerchantRequest newInstance() {
    return new MerchantRequest().selectId().filterByDeleted(false);
  }

  public static MerchantRequest withId(String id) {
    return newInstance().filterById(id);
  }

  @Override
  public String getInternalType() {
    return "Merchant";
  }

  @Override
  public Class<Merchant> entityClass() {
    return Merchant.class;
  }

  public MerchantRequest addSearchCriteria(SearchCriteria pSearchCriteria) {
    doAddSearchCriteria(pSearchCriteria);
    return this;
  }

  public MerchantRequest select(String... names) {
    super.select(names);
    return this;
  }

  public MerchantRequest selectAll() {
    return this.selectId()
        .selectName()
        .selectAdmin()
        .selectEnabled()
        .selectPasswd()
        .selectLocation()
        .selectVersion()
        .selectDeleted();
  }

  public MerchantRequest unselect(String... names) {
    super.unselect(names);
    return this;
  }

  public MerchantRequest where(String property, QueryOperator pQueryOperator, Object... pValue) {
    return addSearchCriteria(new SimplePropertyCriteria(property, pQueryOperator, pValue));
  }

  public MerchantRequest filterById(String id) {
    if (id == null) {
      return this;
    }
    return filterById(QueryOperator.EQUAL, id);
  }

  public MerchantRequest filterById(QueryOperator pQueryOperator, Object... parameters) {
    SearchCriteria searchCriteria = createIdSearchCriteria(pQueryOperator, parameters);
    return addSearchCriteria(searchCriteria);
  }

  public SearchCriteria createIdSearchCriteria(QueryOperator pQueryOperator, Object... parameters) {
    return new SimplePropertyCriteria(Merchant.ID_PROPERTY, pQueryOperator, parameters);
  }

  public MerchantRequest selectId() {
    return select(Merchant.ID_PROPERTY);
  }

  public MerchantRequest unselectId() {
    return unselect(Merchant.ID_PROPERTY);
  }

  public MerchantRequest orderById(boolean asc) {
    addOrderBy(Merchant.ID_PROPERTY, asc);
    return this;
  }

  public MerchantRequest filterByName(String name) {
    if (name == null) {
      return this;
    }
    return filterByName(QueryOperator.EQUAL, name);
  }

  public MerchantRequest filterByName(QueryOperator pQueryOperator, Object... parameters) {
    SearchCriteria searchCriteria = createNameSearchCriteria(pQueryOperator, parameters);
    return addSearchCriteria(searchCriteria);
  }

  public SearchCriteria createNameSearchCriteria(
      QueryOperator pQueryOperator, Object... parameters) {
    return new SimplePropertyCriteria(Merchant.NAME_PROPERTY, pQueryOperator, parameters);
  }

  public MerchantRequest selectName() {
    return select(Merchant.NAME_PROPERTY);
  }

  public MerchantRequest unselectName() {
    return unselect(Merchant.NAME_PROPERTY);
  }

  public MerchantRequest orderByName(boolean asc) {
    addOrderBy(Merchant.NAME_PROPERTY, asc);
    return this;
  }

  public MerchantRequest filterByAdmin(String admin) {
    if (admin == null) {
      return this;
    }
    return filterByAdmin(QueryOperator.EQUAL, admin);
  }

  public MerchantRequest filterByAdmin(QueryOperator pQueryOperator, Object... parameters) {
    SearchCriteria searchCriteria = createAdminSearchCriteria(pQueryOperator, parameters);
    return addSearchCriteria(searchCriteria);
  }

  public SearchCriteria createAdminSearchCriteria(
      QueryOperator pQueryOperator, Object... parameters) {
    return new SimplePropertyCriteria(Merchant.ADMIN_PROPERTY, pQueryOperator, parameters);
  }

  public MerchantRequest selectAdmin() {
    return select(Merchant.ADMIN_PROPERTY);
  }

  public MerchantRequest unselectAdmin() {
    return unselect(Merchant.ADMIN_PROPERTY);
  }

  public MerchantRequest orderByAdmin(boolean asc) {
    addOrderBy(Merchant.ADMIN_PROPERTY, asc);
    return this;
  }

  public MerchantRequest filterByEnabled(Boolean enabled) {
    if (enabled == null) {
      return this;
    }
    return filterByEnabled(QueryOperator.EQUAL, enabled);
  }

  public MerchantRequest filterByEnabled(QueryOperator pQueryOperator, Object... parameters) {
    SearchCriteria searchCriteria = createEnabledSearchCriteria(pQueryOperator, parameters);
    return addSearchCriteria(searchCriteria);
  }

  public SearchCriteria createEnabledSearchCriteria(
      QueryOperator pQueryOperator, Object... parameters) {
    return new SimplePropertyCriteria(Merchant.ENABLED_PROPERTY, pQueryOperator, parameters);
  }

  public MerchantRequest selectEnabled() {
    return select(Merchant.ENABLED_PROPERTY);
  }

  public MerchantRequest unselectEnabled() {
    return unselect(Merchant.ENABLED_PROPERTY);
  }

  public MerchantRequest orderByEnabled(boolean asc) {
    addOrderBy(Merchant.ENABLED_PROPERTY, asc);
    return this;
  }

  public MerchantRequest filterByPasswd(String passwd) {
    if (passwd == null) {
      return this;
    }
    return filterByPasswd(QueryOperator.EQUAL, passwd);
  }

  public MerchantRequest filterByPasswd(QueryOperator pQueryOperator, Object... parameters) {
    SearchCriteria searchCriteria = createPasswdSearchCriteria(pQueryOperator, parameters);
    return addSearchCriteria(searchCriteria);
  }

  public SearchCriteria createPasswdSearchCriteria(
      QueryOperator pQueryOperator, Object... parameters) {
    return new SimplePropertyCriteria(Merchant.PASSWD_PROPERTY, pQueryOperator, parameters);
  }

  public MerchantRequest selectPasswd() {
    return select(Merchant.PASSWD_PROPERTY);
  }

  public MerchantRequest unselectPasswd() {
    return unselect(Merchant.PASSWD_PROPERTY);
  }

  public MerchantRequest orderByPasswd(boolean asc) {
    addOrderBy(Merchant.PASSWD_PROPERTY, asc);
    return this;
  }

  public MerchantRequest filterByVersion(Integer version) {
    if (version == null) {
      return this;
    }
    return filterByVersion(QueryOperator.EQUAL, version);
  }

  public MerchantRequest filterByVersion(QueryOperator pQueryOperator, Object... parameters) {
    SearchCriteria searchCriteria = createVersionSearchCriteria(pQueryOperator, parameters);
    return addSearchCriteria(searchCriteria);
  }

  public SearchCriteria createVersionSearchCriteria(
      QueryOperator pQueryOperator, Object... parameters) {
    return new SimplePropertyCriteria(Merchant.VERSION_PROPERTY, pQueryOperator, parameters);
  }

  public MerchantRequest selectVersion() {
    return select(Merchant.VERSION_PROPERTY);
  }

  public MerchantRequest unselectVersion() {
    return unselect(Merchant.VERSION_PROPERTY);
  }

  public MerchantRequest orderByVersion(boolean asc) {
    addOrderBy(Merchant.VERSION_PROPERTY, asc);
    return this;
  }

  public MerchantRequest filterByDeleted(Boolean deleted) {
    if (deleted == null) {
      return this;
    }
    return filterByDeleted(QueryOperator.EQUAL, deleted);
  }

  public MerchantRequest filterByDeleted(QueryOperator pQueryOperator, Object... parameters) {
    SearchCriteria searchCriteria = createDeletedSearchCriteria(pQueryOperator, parameters);
    return addSearchCriteria(searchCriteria);
  }

  public SearchCriteria createDeletedSearchCriteria(
      QueryOperator pQueryOperator, Object... parameters) {
    return new SimplePropertyCriteria(Merchant.DELETED_PROPERTY, pQueryOperator, parameters);
  }

  public MerchantRequest selectDeleted() {
    return select(Merchant.DELETED_PROPERTY);
  }

  public MerchantRequest unselectDeleted() {
    return unselect(Merchant.DELETED_PROPERTY);
  }

  public MerchantRequest orderByDeleted(boolean asc) {
    addOrderBy(Merchant.DELETED_PROPERTY, asc);
    return this;
  }

  public MerchantRequest filterByPlatform(PlatformRequest platform) {
    return filterByPlatform(
        platform,
        platformList -> platformList.stream().map(Platform::getId).collect(Collectors.toSet()));
  }

  public MerchantRequest filterByPlatform(PlatformRequest platform, IDRefine<Platform> idRefine) {
    return addSearchCriteria(createPlatformSearchCriteria(platform, idRefine));
  }

  public SearchCriteria createPlatformSearchCriteria(
      PlatformRequest platform, IDRefine<Platform> idRefine) {
    return new RefinedIdInCriteria(platform, Merchant.PLATFORM_RAW_PROPERTY, idRefine);
  }

  public MerchantRequest selectPlatform() {
    return selectPlatform(PlatformRequest.newInstance());
  }

  public MerchantRequest unselectPlatform() {
    unselectParent(Merchant.PLATFORM_RAW_PROPERTY, Merchant.PLATFORM_PROPERTY);
    return this;
  }

  public MerchantRequest selectPlatform(PlatformRequest platform) {
    selectParent(Merchant.PLATFORM_RAW_PROPERTY, Merchant.PLATFORM_PROPERTY, platform);
    return this;
  }

  public MerchantRequest selectLocation() {
    select(new EmbeddedProperty(Merchant.class, Merchant.LOCATION_PROPERTY));
    return this;
  }

  public MerchantRequest unselectLocation() {
    unselect(new EmbeddedProperty(Merchant.class, Merchant.LOCATION_PROPERTY));
    return this;
  }

  public MerchantRequest filterByMerchantOrderAsSeller(MerchantOrderRequest merchantOrderAsSeller) {
    return filterByMerchantOrderAsSeller(
        merchantOrderAsSeller,
        merchantOrderListAsSeller ->
            merchantOrderListAsSeller.stream()
                .map(MerchantOrder::getSeller)
                .map(Merchant::getId)
                .collect(Collectors.toSet()));
  }

  public MerchantRequest filterByMerchantOrderAsSeller(
      MerchantOrderRequest merchantOrderAsSeller, IDRefine<MerchantOrder> idRefine) {
    merchantOrderAsSeller.select(MerchantOrder.SELLER_PROPERTY);
    return addSearchCriteria(
        createMerchantOrderListAsSellerSearchCriteria(merchantOrderAsSeller, idRefine));
  }

  public SearchCriteria createMerchantOrderListAsSellerSearchCriteria(
      MerchantOrderRequest merchantOrderAsSeller, IDRefine<MerchantOrder> idRefine) {
    return new RefinedIdInCriteria(merchantOrderAsSeller, Merchant.ID_PROPERTY, idRefine);
  }

  public MerchantRequest selectMerchantOrderListAsSeller(
      MerchantOrderRequest merchantOrderAsSeller) {
    selectChild(
        MerchantOrder.SELLER_RAW_PROPERTY, MerchantOrder.SELLER_PROPERTY, merchantOrderAsSeller);
    return this;
  }

  public MerchantRequest selectMerchantOrderListAsSeller() {
    return selectMerchantOrderListAsSeller(MerchantOrderRequest.newInstance().selectAll());
  }

  public MerchantRequest unselectMerchantOrderListAsSeller() {
    unselectChild(
        MerchantOrder.SELLER_RAW_PROPERTY, MerchantOrder.SELLER_PROPERTY, MerchantOrder.class);
    return this;
  }

  public MerchantRequest filterByMerchantOrderAsBuyer(MerchantOrderRequest merchantOrderAsBuyer) {
    return filterByMerchantOrderAsBuyer(
        merchantOrderAsBuyer,
        merchantOrderListAsBuyer ->
            merchantOrderListAsBuyer.stream()
                .map(MerchantOrder::getBuyer)
                .map(Merchant::getId)
                .collect(Collectors.toSet()));
  }

  public MerchantRequest filterByMerchantOrderAsBuyer(
      MerchantOrderRequest merchantOrderAsBuyer, IDRefine<MerchantOrder> idRefine) {
    merchantOrderAsBuyer.select(MerchantOrder.BUYER_PROPERTY);
    return addSearchCriteria(
        createMerchantOrderListAsBuyerSearchCriteria(merchantOrderAsBuyer, idRefine));
  }

  public SearchCriteria createMerchantOrderListAsBuyerSearchCriteria(
      MerchantOrderRequest merchantOrderAsBuyer, IDRefine<MerchantOrder> idRefine) {
    return new RefinedIdInCriteria(merchantOrderAsBuyer, Merchant.ID_PROPERTY, idRefine);
  }

  public MerchantRequest selectMerchantOrderListAsBuyer(MerchantOrderRequest merchantOrderAsBuyer) {
    selectChild(
        MerchantOrder.BUYER_RAW_PROPERTY, MerchantOrder.BUYER_PROPERTY, merchantOrderAsBuyer);
    return this;
  }

  public MerchantRequest selectMerchantOrderListAsBuyer() {
    return selectMerchantOrderListAsBuyer(MerchantOrderRequest.newInstance().selectAll());
  }

  public MerchantRequest unselectMerchantOrderListAsBuyer() {
    unselectChild(
        MerchantOrder.BUYER_RAW_PROPERTY, MerchantOrder.BUYER_PROPERTY, MerchantOrder.class);
    return this;
  }

  public MerchantRequest limit(int start, int size) {
    setOffset(start);
    setSize(size);
    return this;
  }
}
