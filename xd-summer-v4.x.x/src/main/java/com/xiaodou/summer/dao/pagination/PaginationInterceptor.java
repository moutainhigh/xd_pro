package com.xiaodou.summer.dao.pagination;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import com.xiaodou.summer.dao.pagination.dialect.Dialect;

/**
 * @author zhaoyang
 * @version V1.0
 * @Title:PaginationInterceptor.java
 * @Description:MyBatis分页拦截器
 * @date Jan 17, 2014 10:05:02 AM
 */

@Intercepts({@Signature(type = BaseStatementHandler.class, method = "prepare", args = {
    Connection.class, Integer.class})})
public class PaginationInterceptor implements Interceptor {
  private Dialect dialect = null;

  private String paginationSqlRegEx = ".*ByCond";

  @SuppressWarnings("rawtypes")
  public Object intercept(Invocation invocation) throws Throwable {
    // this.debug("intercept");

    if (invocation.getTarget() instanceof RoutingStatementHandler) {
      RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();
      BaseStatementHandler delegate =
          (BaseStatementHandler) ReflectHelper.getValueByFieldName(statementHandler, "delegate");
      MappedStatement mappedStatement =
          (MappedStatement) ReflectHelper.getValueByFieldName(delegate, "mappedStatement");

      // this.debug("stat id : " + mappedStatement.getId());

      if (mappedStatement.getId().matches(paginationSqlRegEx)) {
        // this.debug("matches");

        BoundSql boundSql = delegate.getBoundSql();
        String sql = boundSql.getSql();
        /**
         * 去掉空格
         */
        sql = sql.replaceAll("\\s{1,}", " ");
        Object parameterObject = boundSql.getParameterObject();
        if (parameterObject != null) {

          Page paginationInfo = null;
          if (parameterObject instanceof Map<?, ?>) {
            paginationInfo = (Page) ((Map<?, ?>) parameterObject).get("paginationInfo");
          } else {
            Field pageField = ReflectHelper.getFieldByFieldName(parameterObject, "paginationInfo");
            if (pageField != null) {
              paginationInfo =
                  (Page) ReflectHelper.getValueByFieldName(parameterObject, "paginationInfo");
            }
          }

          if (paginationInfo != null) {
            int count = 0;

            Connection connection = null;
            PreparedStatement countStmt = null;
            ResultSet rs = null;
            try {
              connection = (Connection) invocation.getArgs()[0];
              String countSql = this.dialect.getCountString(sql);

              // this.debug("count sql : " + countSql);

              countStmt = connection.prepareStatement(countSql);
              // BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql,
              // boundSql.getParameterMappings(), parameterObject);
              setParameters(countStmt, mappedStatement, boundSql, parameterObject);
              rs = countStmt.executeQuery();

              if (rs.next()) {
                count = rs.getInt(1);
              }
            } catch (Exception e) {
              e.printStackTrace();
            } finally {
              try {
                rs.close();
              } catch (Exception e) {
                e.printStackTrace();
              }
              try {
                countStmt.close();
              } catch (Exception e) {
                e.printStackTrace();
              }
            }

            // this.debug("count");

            if (parameterObject instanceof Map<?, ?>) {
              paginationInfo = (Page) ((Map<?, ?>) parameterObject).get("paginationInfo");
              if (paginationInfo == null) {
                paginationInfo = new Page(1, count);
              }
            } else {
              Field pageField =
                  ReflectHelper.getFieldByFieldName(parameterObject, "paginationInfo");
              if (pageField != null) {
                paginationInfo =
                    (Page) ReflectHelper.getValueByFieldName(parameterObject, "paginationInfo");
                if (paginationInfo == null) {
                  paginationInfo = new Page();
                }
                ReflectHelper
                    .setValueByFieldName(parameterObject, "paginationInfo", paginationInfo);
              } else {
                throw new NoSuchFieldException(parameterObject.getClass().getName()
                    + "不存在 PaginationInfo 属性");
              }
            }

            paginationInfo.setTotalCount(count);
            paginationInfo.setTotalPage(((count - 1) / paginationInfo.getPageSize()) + 1);

            String paginationSql =
                this.dialect.getLimitString(sql, paginationInfo.getSkipResults(),
                    paginationInfo.getPageSize());

            ReflectHelper.setValueByFieldName(boundSql, "sql", paginationSql);
          }
        }
      }
    }
    return invocation.proceed();
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  private void setParameters(PreparedStatement ps, MappedStatement mappedStatement,
      BoundSql boundSql, Object parameterObject) throws SQLException {
    ErrorContext.instance().activity("setting parameters")
        .object(mappedStatement.getParameterMap().getId());
    List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
    if (parameterMappings != null) {
      Configuration configuration = mappedStatement.getConfiguration();
      TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
      MetaObject metaObject =
          parameterObject == null ? null : configuration.newMetaObject(parameterObject);
      for (int i = 0; i < parameterMappings.size(); i++) {
        ParameterMapping parameterMapping = parameterMappings.get(i);
        if (parameterMapping.getMode() != ParameterMode.OUT) {
          Object value;
          String propertyName = parameterMapping.getProperty();
          PropertyTokenizer prop = new PropertyTokenizer(propertyName);
          if (parameterObject == null) {
            value = null;
          } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
            value = parameterObject;
          } else if (boundSql.hasAdditionalParameter(propertyName)) {
            value = boundSql.getAdditionalParameter(propertyName);
          } else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)
              && boundSql.hasAdditionalParameter(prop.getName())) {
            value = boundSql.getAdditionalParameter(prop.getName());
            if (value != null) {
              value =
                  configuration.newMetaObject(value).getValue(
                      propertyName.substring(prop.getName().length()));
            }
          } else {
            value = metaObject == null ? null : metaObject.getValue(propertyName);
          }
          TypeHandler typeHandler = parameterMapping.getTypeHandler();
          if (typeHandler == null) {
            throw new ExecutorException("There was no TypeHandler found for parameter "
                + propertyName + " of statement " + mappedStatement.getId());
          }
          typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
        }
      }
    }
  }

  public Object plugin(Object arg0) {
    return Plugin.wrap(arg0, this);
  }

  public void setProperties(Properties p) {}

  protected void debug(String msg) {
    // LogUtil.debug(this.getClass().getName(),msg);
  }

  public Dialect getDialect() {
    return dialect;
  }

  public void setDialect(Dialect dialect) {
    this.dialect = dialect;
  }

  public String getPaginationSqlRegEx() {
    return paginationSqlRegEx;
  }

  public void setPaginationSqlRegEx(String paginationSqlRegEx) {
    this.paginationSqlRegEx = paginationSqlRegEx;
  }


  /**
   * @author zhaoyang
   * @version V1.0
   * @Title:ReflectHelper.java
   * @Description:反射工具类
   * @date Jan 17, 2014 10:21:46 AM
   */

  static class ReflectHelper {
    public static Field getFieldByFieldName(Object obj, String fieldName) {
      for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass =
          superClass.getSuperclass()) {
        try {
          return superClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {}
      }
      return null;
    }

    public static Object getValueByFieldName(Object obj, String fieldName)
        throws SecurityException, NoSuchFieldException, IllegalArgumentException,
        IllegalAccessException {
      Field field = getFieldByFieldName(obj, fieldName);
      Object value = null;
      if (field != null) {
        if (field.isAccessible()) {
          value = field.get(obj);
        } else {
          field.setAccessible(true);
          value = field.get(obj);
          field.setAccessible(false);
        }
      }
      return value;
    }

    public static void setValueByFieldName(Object obj, String fieldName, Object value)
        throws SecurityException, NoSuchFieldException, IllegalArgumentException,
        IllegalAccessException {
      Field field = obj.getClass().getDeclaredField(fieldName);
      if (field.isAccessible()) {
        field.set(obj, value);
      } else {
        field.setAccessible(true);
        field.set(obj, value);
        field.setAccessible(false);
      }
    }
  }

}