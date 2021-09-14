package com.doublechain.bank.ordertype;

import com.doublechain.bank.platform.Platform;
import com.doublechain.bank.platform.PlatformRequest;
import com.doublechaintech.search.BaseRequest;
import com.doublechaintech.search.IDRefine;
import com.doublechaintech.search.QueryOperator;
import com.doublechaintech.search.RefinedIdInCriteria;
import com.doublechaintech.search.SearchCriteria;
import com.doublechaintech.search.SimplePropertyCriteria;
import java.util.stream.Collectors;

public class OrderTypeRequest extends BaseRequest<OrderType> {
  public static OrderTypeRequest newInstance() {
    return new OrderTypeRequest().selectId().filterByDeleted(false);
  }

  public static OrderTypeRequest withId(String id) {
    return newInstance().filterById(id);
  }

  @Override
  public String getInternalType() {
    return "OrderType";
  }

  @Override
  public Class<OrderType> entityClass() {
    return OrderType.class;
  }

  public OrderTypeRequest addSearchCriteria(SearchCriteria pSearchCriteria) {
    doAddSearchCriteria(pSearchCriteria);
    return this;
  }

  public OrderTypeRequest select(String... names) {
    super.select(names);
    return this;
  }

  public OrderTypeRequest selectAll() {
    return this.selectId().selectName().selectCode().selectVersion().selectDeleted();
  }

  public OrderTypeRequest unselect(String... names) {
    super.unselect(names);
    return this;
  }

  public OrderTypeRequest where(String property, QueryOperator pQueryOperator, Object... pValue) {
    return addSearchCriteria(new SimplePropertyCriteria(property, pQueryOperator, pValue));
  }

  public OrderTypeRequest filterById(String id) {
    if (id == null) {
      return this;
    }
    return filterById(QueryOperator.EQUAL, id);
  }

  public OrderTypeRequest filterById(QueryOperator pQueryOperator, Object... parameters) {
    SearchCriteria searchCriteria = createIdSearchCriteria(pQueryOperator, parameters);
    return addSearchCriteria(searchCriteria);
  }

  public SearchCriteria createIdSearchCriteria(QueryOperator pQueryOperator, Object... parameters) {
    return new SimplePropertyCriteria(OrderType.ID_PROPERTY, pQueryOperator, parameters);
  }

  public OrderTypeRequest selectId() {
    return select(OrderType.ID_PROPERTY);
  }

  public OrderTypeRequest unselectId() {
    return unselect(OrderType.ID_PROPERTY);
  }

  public OrderTypeRequest orderById(boolean asc) {
    addOrderBy(OrderType.ID_PROPERTY, asc);
    return this;
  }

  public OrderTypeRequest filterByName(String name) {
    if (name == null) {
      return this;
    }
    return filterByName(QueryOperator.EQUAL, name);
  }

  public OrderTypeRequest filterByName(QueryOperator pQueryOperator, Object... parameters) {
    SearchCriteria searchCriteria = createNameSearchCriteria(pQueryOperator, parameters);
    return addSearchCriteria(searchCriteria);
  }

  public SearchCriteria createNameSearchCriteria(
      QueryOperator pQueryOperator, Object... parameters) {
    return new SimplePropertyCriteria(OrderType.NAME_PROPERTY, pQueryOperator, parameters);
  }

  public OrderTypeRequest selectName() {
    return select(OrderType.NAME_PROPERTY);
  }

  public OrderTypeRequest unselectName() {
    return unselect(OrderType.NAME_PROPERTY);
  }

  public OrderTypeRequest orderByName(boolean asc) {
    addOrderBy(OrderType.NAME_PROPERTY, asc);
    return this;
  }

  public OrderTypeRequest filterByCode(String code) {
    if (code == null) {
      return this;
    }
    return filterByCode(QueryOperator.EQUAL, code);
  }

  public OrderTypeRequest filterByCode(QueryOperator pQueryOperator, Object... parameters) {
    SearchCriteria searchCriteria = createCodeSearchCriteria(pQueryOperator, parameters);
    return addSearchCriteria(searchCriteria);
  }

  public SearchCriteria createCodeSearchCriteria(
      QueryOperator pQueryOperator, Object... parameters) {
    return new SimplePropertyCriteria(OrderType.CODE_PROPERTY, pQueryOperator, parameters);
  }

  public OrderTypeRequest selectCode() {
    return select(OrderType.CODE_PROPERTY);
  }

  public OrderTypeRequest unselectCode() {
    return unselect(OrderType.CODE_PROPERTY);
  }

  public OrderTypeRequest orderByCode(boolean asc) {
    addOrderBy(OrderType.CODE_PROPERTY, asc);
    return this;
  }

  public OrderTypeRequest filterByVersion(Integer version) {
    if (version == null) {
      return this;
    }
    return filterByVersion(QueryOperator.EQUAL, version);
  }

  public OrderTypeRequest filterByVersion(QueryOperator pQueryOperator, Object... parameters) {
    SearchCriteria searchCriteria = createVersionSearchCriteria(pQueryOperator, parameters);
    return addSearchCriteria(searchCriteria);
  }

  public SearchCriteria createVersionSearchCriteria(
      QueryOperator pQueryOperator, Object... parameters) {
    return new SimplePropertyCriteria(OrderType.VERSION_PROPERTY, pQueryOperator, parameters);
  }

  public OrderTypeRequest selectVersion() {
    return select(OrderType.VERSION_PROPERTY);
  }

  public OrderTypeRequest unselectVersion() {
    return unselect(OrderType.VERSION_PROPERTY);
  }

  public OrderTypeRequest orderByVersion(boolean asc) {
    addOrderBy(OrderType.VERSION_PROPERTY, asc);
    return this;
  }

  public OrderTypeRequest filterByDeleted(Boolean deleted) {
    if (deleted == null) {
      return this;
    }
    return filterByDeleted(QueryOperator.EQUAL, deleted);
  }

  public OrderTypeRequest filterByDeleted(QueryOperator pQueryOperator, Object... parameters) {
    SearchCriteria searchCriteria = createDeletedSearchCriteria(pQueryOperator, parameters);
    return addSearchCriteria(searchCriteria);
  }

  public SearchCriteria createDeletedSearchCriteria(
      QueryOperator pQueryOperator, Object... parameters) {
    return new SimplePropertyCriteria(OrderType.DELETED_PROPERTY, pQueryOperator, parameters);
  }

  public OrderTypeRequest selectDeleted() {
    return select(OrderType.DELETED_PROPERTY);
  }

  public OrderTypeRequest unselectDeleted() {
    return unselect(OrderType.DELETED_PROPERTY);
  }

  public OrderTypeRequest orderByDeleted(boolean asc) {
    addOrderBy(OrderType.DELETED_PROPERTY, asc);
    return this;
  }

  public OrderTypeRequest filterByPlatform(PlatformRequest platform) {
    return filterByPlatform(
        platform,
        platformList -> platformList.stream().map(Platform::getId).collect(Collectors.toSet()));
  }

  public OrderTypeRequest filterByPlatform(PlatformRequest platform, IDRefine<Platform> idRefine) {
    return addSearchCriteria(createPlatformSearchCriteria(platform, idRefine));
  }

  public SearchCriteria createPlatformSearchCriteria(
      PlatformRequest platform, IDRefine<Platform> idRefine) {
    return new RefinedIdInCriteria(platform, OrderType.PLATFORM_RAW_PROPERTY, idRefine);
  }

  public OrderTypeRequest selectPlatform() {
    return selectPlatform(PlatformRequest.newInstance());
  }

  public OrderTypeRequest unselectPlatform() {
    unselectParent(OrderType.PLATFORM_RAW_PROPERTY, OrderType.PLATFORM_PROPERTY);
    return this;
  }

  public OrderTypeRequest selectPlatform(PlatformRequest platform) {
    selectParent(OrderType.PLATFORM_RAW_PROPERTY, OrderType.PLATFORM_PROPERTY, platform);
    return this;
  }

  public OrderTypeRequest limit(int start, int size) {
    setOffset(start);
    setSize(size);
    return this;
  }
}
