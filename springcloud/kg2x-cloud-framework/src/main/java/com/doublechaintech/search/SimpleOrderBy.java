package com.doublechaintech.search;

import com.doublechaintech.util.TextUtils;

import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public class SimpleOrderBy implements OrderBy {

  private LinkedHashMap<String, Boolean> properties = new LinkedHashMap<>();

  public LinkedHashMap<String, Boolean> getProperties() {
    return properties;
  }

  public void setProperties(LinkedHashMap<String, Boolean> pProperties) {
    properties = pProperties;
  }

  @Override
  public String toSql() {
    if (properties.isEmpty()) {
      return null;
    }
    return properties.entrySet().stream()
        .map(
            e ->
                String.format(
                    "%s %s",
                    TextUtils.propertyToColumnName(e.getKey()), e.getValue() ? "asc" : "desc"))
        .collect(Collectors.joining(",", "order by ", ""));
  }

  public void addOrderBy(String property, boolean asc) {
    properties.putIfAbsent(property, asc);
  }
}
