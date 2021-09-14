package com.doublechaintech.search;

import com.doublechaintech.BaseEntity;

import java.util.List;
import java.util.Set;

public interface IDRefine<T extends BaseEntity> {
  Set<String> refineId(List<T> list);
}
