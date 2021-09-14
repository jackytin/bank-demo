package com.doublechain.bank.merchantorder;

import com.doublechain.bank.merchant.Merchant;
import com.doublechain.bank.merchant.MerchantRequest;
import com.doublechaintech.search.BaseRequest;
import com.doublechaintech.search.IDRefine;
import com.doublechaintech.search.QueryOperator;
import com.doublechaintech.search.RefinedIdInCriteria;
import com.doublechaintech.search.SearchCriteria;
import com.doublechaintech.search.SimplePropertyCriteria;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class MerchantOrderRequest extends BaseRequest<MerchantOrder> {
  public static MerchantOrderRequest newInstance() {
    return new MerchantOrderRequest().selectId().filterByDeleted(false);
  }

  public static MerchantOrderRequest withId(String id) {
    return newInstance().filterById(id);
  }

  @Override
  public String getInternalType() {
    return "MerchantOrder";
  }

  @Override
  public Class<MerchantOrder> entityClass() {
    return MerchantOrder.class;
  }

  public MerchantOrderRequest addSearchCriteria(SearchCriteria pSearchCriteria) {
    doAddSearchCriteria(pSearchCriteria);
    return this;
  }

  public MerchantOrderRequest select(String... names) {
    super.select(names);
    return this;
  }

  public MerchantOrderRequest selectAll() {
    return this.selectId().selectTotalAmout().selectCreateDate().selectVersion().selectDeleted();
  }

  public MerchantOrderRequest unselect(String... names) {
    super.unselect(names);
    return this;
  }

  public MerchantOrderRequest where(
      String property, QueryOperator pQueryOperator, Object... pValue) {
    return addSearchCriteria(new SimplePropertyCriteria(property, pQueryOperator, pValue));
  }

  public MerchantOrderRequest filterById(String id) {
    if (id == null) {
      return this;
    }
    return filterById(QueryOperator.EQUAL, id);
  }

  public MerchantOrderRequest filterById(QueryOperator pQueryOperator, Object... parameters) {
    SearchCriteria searchCriteria = createIdSearchCriteria(pQueryOperator, parameters);
    return addSearchCriteria(searchCriteria);
  }

  public SearchCriteria createIdSearchCriteria(QueryOperator pQueryOperator, Object... parameters) {
    return new SimplePropertyCriteria(MerchantOrder.ID_PROPERTY, pQueryOperator, parameters);
  }

  public MerchantOrderRequest selectId() {
    return select(MerchantOrder.ID_PROPERTY);
  }

  public MerchantOrderRequest unselectId() {
    return unselect(MerchantOrder.ID_PROPERTY);
  }

  public MerchantOrderRequest orderById(boolean asc) {
    addOrderBy(MerchantOrder.ID_PROPERTY, asc);
    return this;
  }

  public MerchantOrderRequest filterByTotalAmout(BigDecimal totalAmout) {
    if (totalAmout == null) {
      return this;
    }
    return filterByTotalAmout(QueryOperator.EQUAL, totalAmout);
  }

  public MerchantOrderRequest filterByTotalAmout(
      QueryOperator pQueryOperator, Object... parameters) {
    SearchCriteria searchCriteria = createTotalAmoutSearchCriteria(pQueryOperator, parameters);
    return addSearchCriteria(searchCriteria);
  }

  public SearchCriteria createTotalAmoutSearchCriteria(
      QueryOperator pQueryOperator, Object... parameters) {
    return new SimplePropertyCriteria(
        MerchantOrder.TOTAL_AMOUT_PROPERTY, pQueryOperator, parameters);
  }

  public MerchantOrderRequest selectTotalAmout() {
    return select(MerchantOrder.TOTAL_AMOUT_PROPERTY);
  }

  public MerchantOrderRequest unselectTotalAmout() {
    return unselect(MerchantOrder.TOTAL_AMOUT_PROPERTY);
  }

  public MerchantOrderRequest orderByTotalAmout(boolean asc) {
    addOrderBy(MerchantOrder.TOTAL_AMOUT_PROPERTY, asc);
    return this;
  }

  public MerchantOrderRequest filterByCreateDate(LocalDate createDate) {
    if (createDate == null) {
      return this;
    }
    return filterByCreateDate(QueryOperator.EQUAL, createDate);
  }

  public MerchantOrderRequest filterByCreateDate(
      QueryOperator pQueryOperator, Object... parameters) {
    SearchCriteria searchCriteria = createCreateDateSearchCriteria(pQueryOperator, parameters);
    return addSearchCriteria(searchCriteria);
  }

  public SearchCriteria createCreateDateSearchCriteria(
      QueryOperator pQueryOperator, Object... parameters) {
    return new SimplePropertyCriteria(
        MerchantOrder.CREATE_DATE_PROPERTY, pQueryOperator, parameters);
  }

  public MerchantOrderRequest selectCreateDate() {
    return select(MerchantOrder.CREATE_DATE_PROPERTY);
  }

  public MerchantOrderRequest unselectCreateDate() {
    return unselect(MerchantOrder.CREATE_DATE_PROPERTY);
  }

  public MerchantOrderRequest orderByCreateDate(boolean asc) {
    addOrderBy(MerchantOrder.CREATE_DATE_PROPERTY, asc);
    return this;
  }

  public MerchantOrderRequest filterByVersion(Integer version) {
    if (version == null) {
      return this;
    }
    return filterByVersion(QueryOperator.EQUAL, version);
  }

  public MerchantOrderRequest filterByVersion(QueryOperator pQueryOperator, Object... parameters) {
    SearchCriteria searchCriteria = createVersionSearchCriteria(pQueryOperator, parameters);
    return addSearchCriteria(searchCriteria);
  }

  public SearchCriteria createVersionSearchCriteria(
      QueryOperator pQueryOperator, Object... parameters) {
    return new SimplePropertyCriteria(MerchantOrder.VERSION_PROPERTY, pQueryOperator, parameters);
  }

  public MerchantOrderRequest selectVersion() {
    return select(MerchantOrder.VERSION_PROPERTY);
  }

  public MerchantOrderRequest unselectVersion() {
    return unselect(MerchantOrder.VERSION_PROPERTY);
  }

  public MerchantOrderRequest orderByVersion(boolean asc) {
    addOrderBy(MerchantOrder.VERSION_PROPERTY, asc);
    return this;
  }

  public MerchantOrderRequest filterByDeleted(Boolean deleted) {
    if (deleted == null) {
      return this;
    }
    return filterByDeleted(QueryOperator.EQUAL, deleted);
  }

  public MerchantOrderRequest filterByDeleted(QueryOperator pQueryOperator, Object... parameters) {
    SearchCriteria searchCriteria = createDeletedSearchCriteria(pQueryOperator, parameters);
    return addSearchCriteria(searchCriteria);
  }

  public SearchCriteria createDeletedSearchCriteria(
      QueryOperator pQueryOperator, Object... parameters) {
    return new SimplePropertyCriteria(MerchantOrder.DELETED_PROPERTY, pQueryOperator, parameters);
  }

  public MerchantOrderRequest selectDeleted() {
    return select(MerchantOrder.DELETED_PROPERTY);
  }

  public MerchantOrderRequest unselectDeleted() {
    return unselect(MerchantOrder.DELETED_PROPERTY);
  }

  public MerchantOrderRequest orderByDeleted(boolean asc) {
    addOrderBy(MerchantOrder.DELETED_PROPERTY, asc);
    return this;
  }

  public MerchantOrderRequest filterBySeller(MerchantRequest seller) {
    return filterBySeller(
        seller, sellerList -> sellerList.stream().map(Merchant::getId).collect(Collectors.toSet()));
  }

  public MerchantOrderRequest filterBySeller(MerchantRequest seller, IDRefine<Merchant> idRefine) {
    return addSearchCriteria(createSellerSearchCriteria(seller, idRefine));
  }

  public SearchCriteria createSellerSearchCriteria(
      MerchantRequest seller, IDRefine<Merchant> idRefine) {
    return new RefinedIdInCriteria(seller, MerchantOrder.SELLER_RAW_PROPERTY, idRefine);
  }

  public MerchantOrderRequest selectSeller() {
    return selectSeller(MerchantRequest.newInstance());
  }

  public MerchantOrderRequest unselectSeller() {
    unselectParent(MerchantOrder.SELLER_RAW_PROPERTY, MerchantOrder.SELLER_PROPERTY);
    return this;
  }

  public MerchantOrderRequest selectSeller(MerchantRequest seller) {
    selectParent(MerchantOrder.SELLER_RAW_PROPERTY, MerchantOrder.SELLER_PROPERTY, seller);
    return this;
  }

  public MerchantOrderRequest filterByBuyer(MerchantRequest buyer) {
    return filterByBuyer(
        buyer, buyerList -> buyerList.stream().map(Merchant::getId).collect(Collectors.toSet()));
  }

  public MerchantOrderRequest filterByBuyer(MerchantRequest buyer, IDRefine<Merchant> idRefine) {
    return addSearchCriteria(createBuyerSearchCriteria(buyer, idRefine));
  }

  public SearchCriteria createBuyerSearchCriteria(
      MerchantRequest buyer, IDRefine<Merchant> idRefine) {
    return new RefinedIdInCriteria(buyer, MerchantOrder.BUYER_RAW_PROPERTY, idRefine);
  }

  public MerchantOrderRequest selectBuyer() {
    return selectBuyer(MerchantRequest.newInstance());
  }

  public MerchantOrderRequest unselectBuyer() {
    unselectParent(MerchantOrder.BUYER_RAW_PROPERTY, MerchantOrder.BUYER_PROPERTY);
    return this;
  }

  public MerchantOrderRequest selectBuyer(MerchantRequest buyer) {
    selectParent(MerchantOrder.BUYER_RAW_PROPERTY, MerchantOrder.BUYER_PROPERTY, buyer);
    return this;
  }

  public MerchantOrderRequest limit(int start, int size) {
    setOffset(start);
    setSize(size);
    return this;
  }
}
