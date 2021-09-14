package com.doublechaintech.usercontext;

import java.util.Date;

public interface UserContext {
  String getName();

  String getId();

  Date now();

  <T extends UserContext> T me();
}
