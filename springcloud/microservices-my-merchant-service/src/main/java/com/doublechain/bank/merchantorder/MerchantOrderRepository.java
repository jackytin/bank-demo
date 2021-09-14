package com.doublechain.bank.merchantorder;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface MerchantOrderRepository
    extends PagingAndSortingRepository<MerchantOrder, String> {}
