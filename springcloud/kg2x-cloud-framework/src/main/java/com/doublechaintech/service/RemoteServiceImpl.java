package com.doublechaintech.service;

import com.doublechaintech.BaseEntity;
import com.doublechaintech.EntityExecutionContext;
import com.doublechaintech.EntityIdentifier;
import com.doublechaintech.search.BaseRequest;
import com.doublechaintech.usercontext.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/entityService")
public class RemoteServiceImpl {

  public RemoteServiceImpl(@Autowired @Qualifier("baseService") ServiceAdapter pServiceAdapter) {
    mServiceAdapter = pServiceAdapter;
  }

  private ServiceAdapter mServiceAdapter;

  @Autowired private UserContext userContext;

  @PostMapping("/search")
  @ResponseBody
  public List<BaseEntity> search(@RequestBody BaseRequest request) {
    return mServiceAdapter.query(userContext, request);
  }

  @PostMapping("/lookup")
  @ResponseBody
  public BaseEntity lookup(@RequestBody EntityIdentifier request) {
    return mServiceAdapter.lookup(userContext, request);
  }

  @PostMapping("/processAction")
  @ResponseBody
  public EntityExecutionContext processAction(@RequestBody EntityExecutionContext context) {
    mServiceAdapter.executeInternal(
        context.getUserContext(),
        context.getHandled(),
        context.getSavedPlainEntities(),
        context.getEntity(),
        context.getDefaultAction());
    return context;
  }
}
