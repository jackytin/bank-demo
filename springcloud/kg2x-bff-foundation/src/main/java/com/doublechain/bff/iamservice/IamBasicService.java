package com.doublechain.bff.iamservice;

import com.doublechaintech.iamservice.LoginContext;
import com.doublechaintech.response.Response;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IamBasicService {

  @PostMapping(value = "/loginWith")
  public Response loginWith(
      @RequestBody(required = false) LoginContext loginContext,
      @RequestHeader("Authentication") String authToken);
}
