package com.doublechaintech.search;

import com.doublechaintech.BaseEntity;
import com.doublechaintech.service.BaseService;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RefinedIdInCriteria<T extends BaseEntity> implements SearchCriteria {
  private BaseRequest<T> mDependsOn;
  private String mProperty;
  private IDRefine<T> mIdRefine;

  public RefinedIdInCriteria(BaseRequest<T> pDependsOn, String pProperty, IDRefine<T> pIdRefine) {
    mDependsOn = pDependsOn;
    mProperty = pProperty;
    mIdRefine = pIdRefine;
  }

  @Override
  public String prepareParameterAndSql(Map<String, Object> parameters) {
    JdbcMappingContext jdbcMappingContext =
        (JdbcMappingContext) parameters.get("jdbcMappingContext");
    Class entityClass = (Class) parameters.get("entityClass");
    BaseService baseService = (BaseService) parameters.get("baseService");
    List<T> results = baseService.query(parameters.get("userContext"), mDependsOn);
    Set<String> refineIds = mIdRefine.refineId(results);
    String columnName = SearchCriteria.getColumnName(jdbcMappingContext, entityClass, mProperty);
    String parameterName = refineParameterName(parameters, mProperty);
    if (refineIds == null || refineIds.isEmpty()) {
      return "false";
    } else if (refineIds.size() == 1) {
      parameters.put(parameterName, refineIds.iterator().next());
      return String.format("(%s = :%s)", columnName, parameterName);
    } else {
      parameters.put(parameterName, refineIds);
      return String.format("(%s in (:%s))", columnName, parameterName);
    }
  }
}
