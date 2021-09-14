package com.doublechain.bank.platform;

import com.doublechain.bank.merchant.Merchant;
import com.doublechain.bank.merchant.MerchantRequest;
import com.doublechain.bank.ordertype.OrderType;
import com.doublechain.bank.ordertype.OrderTypeRequest;
import com.doublechaintech.search.BaseRequest;
import com.doublechaintech.search.IDRefine;
import com.doublechaintech.search.QueryOperator;
import com.doublechaintech.search.RefinedIdInCriteria;
import com.doublechaintech.search.SearchCriteria;
import com.doublechaintech.search.SimplePropertyCriteria;
import java.util.stream.Collectors;

public class PlatformRequest extends BaseRequest<Platform> {
  public static PlatformRequest newInstance() {
    return new PlatformRequest().selectId().filterByDeleted(false);
  }

  public static PlatformRequest withId(String id) {
    return newInstance().filterById(id);
  }

  @Override
  public String getInternalType() {
    return "Platform";
  }

  @Override
  public Class<Platform> entityClass() {
    return Platform.class;
  }

  public PlatformRequest addSearchCriteria(SearchCriteria pSearchCriteria) {
    doAddSearchCriteria(pSearchCriteria);
    return this;
  }

  public PlatformRequest select(String... names) {
    super.select(names);
    return this;
  }

  public PlatformRequest selectAll() {
    return this.selectId()
        .selectName()
        .selectFounded()
        .selectContactNumber()
        .selectVersion()
        .selectDeleted();
  }

  public PlatformRequest unselect(String... names) {
    super.unselect(names);
    return this;
  }

  public PlatformRequest where(String property, QueryOperator pQueryOperator, Object... pValue) {
    return addSearchCriteria(new SimplePropertyCriteria(property, pQueryOperator, pValue));
  }

  public PlatformRequest filterById(String id) {
    if (id == null) {
      return this;
    }
    return filterById(QueryOperator.EQUAL, id);
  }

  public PlatformRequest filterById(QueryOperator pQueryOperator, Object... parameters) {
    SearchCriteria searchCriteria = createIdSearchCriteria(pQueryOperator, parameters);
    return addSearchCriteria(searchCriteria);
  }

  public SearchCriteria createIdSearchCriteria(QueryOperator pQueryOperator, Object... parameters) {
    return new SimplePropertyCriteria(Platform.ID_PROPERTY, pQueryOperator, parameters);
  }

  public PlatformRequest selectId() {
    return select(Platform.ID_PROPERTY);
  }

  public PlatformRequest unselectId() {
    return unselect(Platform.ID_PROPERTY);
  }

  public PlatformRequest orderById(boolean asc) {
    addOrderBy(Platform.ID_PROPERTY, asc);
    return this;
  }

  public PlatformRequest filterByName(String name) {
    if (name == null) {
      return this;
    }
    return filterByName(QueryOperator.EQUAL, name);
  }

  public PlatformRequest filterByName(QueryOperator pQueryOperator, Object... parameters) {
    SearchCriteria searchCriteria = createNameSearchCriteria(pQueryOperator, parameters);
    return addSearchCriteria(searchCriteria);
  }

  public SearchCriteria createNameSearchCriteria(
      QueryOperator pQueryOperator, Object... parameters) {
    return new SimplePropertyCriteria(Platform.NAME_PROPERTY, pQueryOperator, parameters);
  }

  public PlatformRequest selectName() {
    return select(Platform.NAME_PROPERTY);
  }

  public PlatformRequest unselectName() {
    return unselect(Platform.NAME_PROPERTY);
  }

  public PlatformRequest orderByName(boolean asc) {
    addOrderBy(Platform.NAME_PROPERTY, asc);
    return this;
  }

  public PlatformRequest filterByFounded(String founded) {
    if (founded == null) {
      return this;
    }
    return filterByFounded(QueryOperator.EQUAL, founded);
  }

  public PlatformRequest filterByFounded(QueryOperator pQueryOperator, Object... parameters) {
    SearchCriteria searchCriteria = createFoundedSearchCriteria(pQueryOperator, parameters);
    return addSearchCriteria(searchCriteria);
  }

  public SearchCriteria createFoundedSearchCriteria(
      QueryOperator pQueryOperator, Object... parameters) {
    return new SimplePropertyCriteria(Platform.FOUNDED_PROPERTY, pQueryOperator, parameters);
  }

  public PlatformRequest selectFounded() {
    return select(Platform.FOUNDED_PROPERTY);
  }

  public PlatformRequest unselectFounded() {
    return unselect(Platform.FOUNDED_PROPERTY);
  }

  public PlatformRequest orderByFounded(boolean asc) {
    addOrderBy(Platform.FOUNDED_PROPERTY, asc);
    return this;
  }

  public PlatformRequest filterByContactNumber(Long contactNumber) {
    if (contactNumber == null) {
      return this;
    }
    return filterByContactNumber(QueryOperator.EQUAL, contactNumber);
  }

  public PlatformRequest filterByContactNumber(QueryOperator pQueryOperator, Object... parameters) {
    SearchCriteria searchCriteria = createContactNumberSearchCriteria(pQueryOperator, parameters);
    return addSearchCriteria(searchCriteria);
  }

  public SearchCriteria createContactNumberSearchCriteria(
      QueryOperator pQueryOperator, Object... parameters) {
    return new SimplePropertyCriteria(Platform.CONTACT_NUMBER_PROPERTY, pQueryOperator, parameters);
  }

  public PlatformRequest selectContactNumber() {
    return select(Platform.CONTACT_NUMBER_PROPERTY);
  }

  public PlatformRequest unselectContactNumber() {
    return unselect(Platform.CONTACT_NUMBER_PROPERTY);
  }

  public PlatformRequest orderByContactNumber(boolean asc) {
    addOrderBy(Platform.CONTACT_NUMBER_PROPERTY, asc);
    return this;
  }

  public PlatformRequest filterByVersion(Integer version) {
    if (version == null) {
      return this;
    }
    return filterByVersion(QueryOperator.EQUAL, version);
  }

  public PlatformRequest filterByVersion(QueryOperator pQueryOperator, Object... parameters) {
    SearchCriteria searchCriteria = createVersionSearchCriteria(pQueryOperator, parameters);
    return addSearchCriteria(searchCriteria);
  }

  public SearchCriteria createVersionSearchCriteria(
      QueryOperator pQueryOperator, Object... parameters) {
    return new SimplePropertyCriteria(Platform.VERSION_PROPERTY, pQueryOperator, parameters);
  }

  public PlatformRequest selectVersion() {
    return select(Platform.VERSION_PROPERTY);
  }

  public PlatformRequest unselectVersion() {
    return unselect(Platform.VERSION_PROPERTY);
  }

  public PlatformRequest orderByVersion(boolean asc) {
    addOrderBy(Platform.VERSION_PROPERTY, asc);
    return this;
  }

  public PlatformRequest filterByDeleted(Boolean deleted) {
    if (deleted == null) {
      return this;
    }
    return filterByDeleted(QueryOperator.EQUAL, deleted);
  }

  public PlatformRequest filterByDeleted(QueryOperator pQueryOperator, Object... parameters) {
    SearchCriteria searchCriteria = createDeletedSearchCriteria(pQueryOperator, parameters);
    return addSearchCriteria(searchCriteria);
  }

  public SearchCriteria createDeletedSearchCriteria(
      QueryOperator pQueryOperator, Object... parameters) {
    return new SimplePropertyCriteria(Platform.DELETED_PROPERTY, pQueryOperator, parameters);
  }

  public PlatformRequest selectDeleted() {
    return select(Platform.DELETED_PROPERTY);
  }

  public PlatformRequest unselectDeleted() {
    return unselect(Platform.DELETED_PROPERTY);
  }

  public PlatformRequest orderByDeleted(boolean asc) {
    addOrderBy(Platform.DELETED_PROPERTY, asc);
    return this;
  }

  public PlatformRequest filterByMerchant(MerchantRequest merchant) {
    return filterByMerchant(
        merchant,
        merchantList ->
            merchantList.stream()
                .map(Merchant::getPlatform)
                .map(Platform::getId)
                .collect(Collectors.toSet()));
  }

  public PlatformRequest filterByMerchant(MerchantRequest merchant, IDRefine<Merchant> idRefine) {
    merchant.select(Merchant.PLATFORM_PROPERTY);
    return addSearchCriteria(createMerchantListSearchCriteria(merchant, idRefine));
  }

  public SearchCriteria createMerchantListSearchCriteria(
      MerchantRequest merchant, IDRefine<Merchant> idRefine) {
    return new RefinedIdInCriteria(merchant, Platform.ID_PROPERTY, idRefine);
  }

  public PlatformRequest selectMerchantList(MerchantRequest merchant) {
    selectChild(Merchant.PLATFORM_RAW_PROPERTY, Merchant.PLATFORM_PROPERTY, merchant);
    return this;
  }

  public PlatformRequest selectMerchantList() {
    return selectMerchantList(MerchantRequest.newInstance().selectAll());
  }

  public PlatformRequest unselectMerchantList() {
    unselectChild(Merchant.PLATFORM_RAW_PROPERTY, Merchant.PLATFORM_PROPERTY, Merchant.class);
    return this;
  }

  public PlatformRequest filterByOrderType(OrderTypeRequest orderType) {
    return filterByOrderType(
        orderType,
        orderTypeList ->
            orderTypeList.stream()
                .map(OrderType::getPlatform)
                .map(Platform::getId)
                .collect(Collectors.toSet()));
  }

  public PlatformRequest filterByOrderType(
      OrderTypeRequest orderType, IDRefine<OrderType> idRefine) {
    orderType.select(OrderType.PLATFORM_PROPERTY);
    return addSearchCriteria(createOrderTypeListSearchCriteria(orderType, idRefine));
  }

  public SearchCriteria createOrderTypeListSearchCriteria(
      OrderTypeRequest orderType, IDRefine<OrderType> idRefine) {
    return new RefinedIdInCriteria(orderType, Platform.ID_PROPERTY, idRefine);
  }

  public PlatformRequest selectOrderTypeList(OrderTypeRequest orderType) {
    selectChild(OrderType.PLATFORM_RAW_PROPERTY, OrderType.PLATFORM_PROPERTY, orderType);
    return this;
  }

  public PlatformRequest selectOrderTypeList() {
    return selectOrderTypeList(OrderTypeRequest.newInstance().selectAll());
  }

  public PlatformRequest unselectOrderTypeList() {
    unselectChild(OrderType.PLATFORM_RAW_PROPERTY, OrderType.PLATFORM_PROPERTY, OrderType.class);
    return this;
  }

  public PlatformRequest limit(int start, int size) {
    setOffset(start);
    setSize(size);
    return this;
  }
}
