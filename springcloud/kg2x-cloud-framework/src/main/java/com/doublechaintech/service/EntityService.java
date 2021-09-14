package com.doublechaintech.service;

import com.doublechaintech.BaseEntity;
import com.doublechaintech.EntityIdentifier;
import com.doublechaintech.EntitySimpleView;
import com.doublechaintech.UpdateRequest;
import com.doublechaintech.search.BaseRequest;

import java.util.List;

/** search/update Entity with request */
public interface EntityService {

  <T extends BaseEntity> List<T> query(Object userContext, BaseRequest<T> pRequest);

  List<EntitySimpleView> querySimpleView(Object userContext, EntityIdentifier... entities);

  <T extends BaseEntity> T lookup(Object userContext, EntityIdentifier pIdentifier);

  <T extends BaseEntity> T update(Object userContext, UpdateRequest request);
}
