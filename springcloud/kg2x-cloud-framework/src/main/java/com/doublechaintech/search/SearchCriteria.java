package com.doublechaintech.search;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.relational.core.mapping.RelationalPersistentEntity;
import org.springframework.data.relational.core.mapping.RelationalPersistentProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public interface SearchCriteria {
  String prepareParameterAndSql(Map<String, Object> parameters);

  default String refineParameterName(Map<String, Object> parameters, String parameterName) {
    while (parameters.containsKey(parameterName)) {
      parameterName = increaseKey(parameterName);
    }
    return parameterName;
  }

  static String getTableName(JdbcMappingContext pJdbcMappingContext, Class pEntityClass) {
    return pJdbcMappingContext.getPersistentEntity(pEntityClass).getTableName().getReference();
  }

  static String getColumnName(
      JdbcMappingContext pJdbcMappingContext, Class pEntityClass, String pProperty) {
    RelationalPersistentEntity<?> persistentEntity =
        pJdbcMappingContext.getPersistentEntity(pEntityClass);

    if (persistentEntity == null) {
      throw new RuntimeException(String.format("class %s is not an entity", pEntityClass));
    }
    RelationalPersistentProperty persistentProperty =
        persistentEntity.getPersistentProperty(pProperty);
    if (persistentProperty == null) {
      throw new RuntimeException(
          String.format("class %s does not have persistence property %s", pEntityClass, pProperty));
    }
    return persistentProperty.getColumnName().getReference();
  }

  default String increaseKey(String key) {
    char c = key.charAt(key.length() - 1);
    if (!Character.isDigit(c)) {
      return key + "0";
    } else {
      // 基本可用
      return key.substring(0, key.length() - 1) + (char) (c + 1);
    }
  }

  static SearchCriteria and(SearchCriteria... sub) {
    return new AddCriteria(sub);
  }

  static SearchCriteria or(SearchCriteria... sub) {
    return new OrCriteria(sub);
  }

  static SearchCriteria not(SearchCriteria sub) {
    return new NotCriteria(sub);
  }

  class AddCriteria implements SearchCriteria {
    private SearchCriteria[] subs;

    public SearchCriteria[] getSubs() {
      return subs;
    }

    public void setSubs(SearchCriteria[] pSubs) {
      subs = pSubs;
    }

    public AddCriteria() {}

    public AddCriteria(SearchCriteria[] subs) {
      this.subs = subs;
    }

    @Override
    public String prepareParameterAndSql(Map<String, Object> parameters) {
      if (subs == null) {
        throw new RuntimeException("AddCriteria 至少需要一个条件");
      }

      if (subs.length == 1) {
        return subs[0].prepareParameterAndSql(parameters);
      }

      List<String> sqls = new ArrayList<>();
      for (SearchCriteria sub : subs) {
        String s = sub.prepareParameterAndSql(parameters);
        if (s.equalsIgnoreCase("true") || s.equals("1")) {
          continue;
        }
        if (s.equalsIgnoreCase("false") || s.equals("0")) {
          return "false";
        }
        sqls.add(s);
      }
      return sqls.stream().collect(Collectors.joining(" and ", "(", ")"));
    }
  }

  class OrCriteria implements SearchCriteria {
    private SearchCriteria[] subs;

    public SearchCriteria[] getSubs() {
      return subs;
    }

    public void setSubs(SearchCriteria[] pSubs) {
      subs = pSubs;
    }

    public OrCriteria() {}

    public OrCriteria(SearchCriteria[] subs) {
      this.subs = subs;
    }

    @Override
    public String prepareParameterAndSql(Map<String, Object> parameters) {
      if (subs == null) {
        throw new RuntimeException("OrCriteria 至少需要一个条件");
      }

      if (subs.length == 1) {
        return subs[0].prepareParameterAndSql(parameters);
      }

      List<String> sqls = new ArrayList<>();
      for (SearchCriteria sub : subs) {
        String s = sub.prepareParameterAndSql(parameters);
        if (s.equalsIgnoreCase("true") || s.equals("1")) {
          return "true";
        }
        if (s.equalsIgnoreCase("false") || s.equals("0")) {
          continue;
        }
        sqls.add(s);
      }

      return sqls.stream().collect(Collectors.joining(" or ", "(", ")"));
    }
  }

  class NotCriteria implements SearchCriteria {
    private SearchCriteria inner;

    public NotCriteria(SearchCriteria inner) {
      this.inner = inner;
    }

    public NotCriteria() {}

    public SearchCriteria getInner() {
      return inner;
    }

    public void setInner(SearchCriteria pInner) {
      inner = pInner;
    }

    @Override
    public String prepareParameterAndSql(Map<String, Object> parameters) {
      String s = inner.prepareParameterAndSql(parameters);
      if (s.equalsIgnoreCase("true") || s.equals("1")) {
        return "false";
      }
      if (s.equalsIgnoreCase("false") || s.equals("0")) {
        return "true";
      }
      return String.format("(not(%s))", inner.prepareParameterAndSql(parameters));
    }
  }
}
