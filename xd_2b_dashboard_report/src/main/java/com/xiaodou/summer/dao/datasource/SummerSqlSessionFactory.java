/*
 * Copyright 2015-2115 eLong Multiple SqlSessionFactory Expand
 */
package com.xiaodou.summer.dao.datasource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.xiaodou.summer.dao.transaction.SummerTransactionManager;

/**
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2015年1月8日
 */
public class SummerSqlSessionFactory implements SqlSessionFactory {

	private final Log logger = LogFactory.getLog(getClass());

	private Resource configLocation;

	private Resource[] mapperLocations;

	private SummerDataSourceManager dataSourceManager;

	private SummerTransactionManager transactionFactoryManager;

	private Properties configurationProperties;

	private SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
	
	private SqlSessionFactory msqlSessionFactory;

	private SqlSessionFactory ssqlSessionFactory;

	private String menvironment = String.format("%s-%s", SummerSqlSessionFactory.class.getSimpleName(), "M");
	
	private String senvironment = String.format("%s-%s", SummerSqlSessionFactory.class.getSimpleName(), "S");

	private boolean failFast;

	private Interceptor[] plugins;

	private TypeHandler[] typeHandlers;

	private String typeHandlersPackage;

	private Class<?>[] typeAliases;

	private String typeAliasesPackage;

	/**
	 * Mybatis plugin list.
	 * 
	 * @since 1.0.1
	 * 
	 * @param plugins
	 *            list of plugins
	 * 
	 */
	public void setPlugins(Interceptor[] plugins) {
		this.plugins = plugins;
	}

	/**
	 * Packages to search for type aliases.
	 * 
	 * @since 1.0.1
	 * 
	 * @param typeAliasesPackage
	 *            package to scan for domain objects
	 * 
	 */
	public void setTypeAliasesPackage(String typeAliasesPackage) {
		this.typeAliasesPackage = typeAliasesPackage;
	}

	/**
	 * Packages to search for type handlers.
	 * 
	 * @since 1.0.1
	 * 
	 * @param typeHandlersPackage
	 *            package to scan for type handlers
	 * 
	 */
	public void setTypeHandlersPackage(String typeHandlersPackage) {
		this.typeHandlersPackage = typeHandlersPackage;
	}

	/**
	 * Set type handlers. They must be annotated with {@code MappedTypes} and
	 * optionally with {@code MappedJdbcTypes}
	 * 
	 * @since 1.0.1
	 * 
	 * @param typeHandlers
	 *            Type handler list
	 */
	public void setTypeHandlers(TypeHandler[] typeHandlers) {
		this.typeHandlers = typeHandlers;
	}

	/**
	 * List of type aliases to register. They can be annotated with
	 * {@code Alias}
	 * 
	 * @since 1.0.1
	 * 
	 * @param typeAliases
	 *            Type aliases list
	 */
	public void setTypeAliases(Class<?>[] typeAliases) {
		this.typeAliases = typeAliases;
	}

	/**
	 * If true, a final check is done on Configuration to assure that all mapped
	 * statements are fully loaded and there is no one still pending to resolve
	 * includes. Defaults to false.
	 * 
	 * @since 1.0.1
	 * 
	 * @param failFast
	 *            enable failFast
	 */
	public void setFailFast(boolean failFast) {
		this.failFast = failFast;
	}

	/**
	 * Set the location of the MyBatis {@code SqlSessionFactory} config file. A
	 * typical value is "WEB-INF/mybatis-configuration.xml".
	 */
	public void setConfigLocation(Resource configLocation) {
		this.configLocation = configLocation;
	}

	/**
	 * Set locations of MyBatis mapper files that are going to be merged into
	 * the {@code SqlSessionFactory} configuration at runtime.
	 * 
	 * This is an alternative to specifying "&lt;sqlmapper&gt;" entries in an
	 * MyBatis config file. This property being based on Spring's resource
	 * abstraction also allows for specifying resource patterns here: e.g.
	 * "classpath*:sqlmap/*-mapper.xml".
	 */
	public void setMapperLocations(Resource[] mapperLocations) {
		this.mapperLocations = mapperLocations;
	}

	/**
	 * Set optional properties to be passed into the SqlSession configuration,
	 * as alternative to a {@code &lt;properties&gt;} tag in the configuration
	 * xml file. This will be used to resolve placeholders in the config file.
	 */
	public void setConfigurationProperties(
			Properties sqlSessionFactoryProperties) {
		this.configurationProperties = sqlSessionFactoryProperties;
	}

	/**
	 * Set the JDBC {@code DataSource} that this instance should manage
	 * transactions for. The {@code DataSource} should match the one used by the
	 * {@code SqlSessionFactory}: for example, you could specify the same JNDI
	 * DataSource for both.
	 * 
	 * A transactional JDBC {@code Connection} for this {@code DataSource} will
	 * be provided to application code accessing this {@code DataSource}
	 * directly via {@code DataSourceUtils} or
	 * {@code DataSourceTransactionManager}.
	 * 
	 * The {@code DataSource} specified here should be the target
	 * {@code DataSource} to manage transactions for, not a
	 * {@code TransactionAwareDataSourceProxy}. Only data access code may work
	 * with {@code TransactionAwareDataSourceProxy}, while the transaction
	 * manager needs to work on the underlying target {@code DataSource}. If
	 * there's nevertheless a {@code TransactionAwareDataSourceProxy} passed in,
	 * it will be unwrapped to extract its target {@code DataSource}.
	 * 
	 */
	public void setDataSourceManager(SummerDataSourceManager dataSourceManager) {
		this.dataSourceManager = dataSourceManager;
	}

	/**
	 * Sets the {@code SqlSessionFactoryBuilder} to use when creating the
	 * {@code SqlSessionFactory}.
	 * 
	 * This is mainly meant for testing so that mock SqlSessionFactory classes
	 * can be injected. By default, {@code SqlSessionFactoryBuilder} creates
	 * {@code DefaultSqlSessionFactory} instances.
	 * 
	 */
	public void setSqlSessionFactoryBuilder(
			SqlSessionFactoryBuilder sqlSessionFactoryBuilder) {
		this.sqlSessionFactoryBuilder = sqlSessionFactoryBuilder;
	}

	/**
	 * Set the MyBatis TransactionFactory to use. Default is
	 * {@code SpringManagedTransactionFactory}
	 * 
	 * The default {@code SpringManagedTransactionFactory} should be appropriate
	 * for all cases: be it Spring transaction management, EJB CMT or plain JTA.
	 * If there is no active transaction, SqlSession operations will execute SQL
	 * statements non-transactionally.
	 * 
	 * <b>It is strongly recommended to use the default
	 * {@code TransactionFactory}.</b> If not used, any attempt at getting an
	 * SqlSession through Spring's MyBatis framework will throw an exception if
	 * a transaction is active.
	 * 
	 * @see SpringManagedTransactionFactory
	 * @param transactionFactory
	 *            the MyBatis TransactionFactory
	 */
	public void setTransactionFactoryManager(
			SummerTransactionManager transactionFactoryManager) {
		this.transactionFactoryManager = transactionFactoryManager;
	}

	/**
	 * <b>NOTE:</b> This class <em>overrides</em> any {@code Environment} you
	 * have set in the MyBatis config file. This is used only as a placeholder
	 * name. The default value is
	 * {@code SqlSessionFactoryBean.class.getSimpleName()}.
	 * 
	 * @param environment
	 *            the environment name
	 */
	public void setEnvironment(String environment) {
		this.menvironment = String.format("%s-%s", environment, "M");
		this.senvironment = String.format("%s-%s", environment, "S");
	}

	/**
	 * {@inheritDoc}
	 */
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(dataSourceManager,
				"Property 'dataSourceManager' is required");
		Assert.notNull(sqlSessionFactoryBuilder,
				"Property 'sqlSessionFactoryBuilder' is required");
		buildSqlSessionFactory();
	}

	/**
	 * Build a {@code SqlSessionFactory} instance.
	 * 
	 * The default implementation uses the standard MyBatis
	 * {@code XMLConfigBuilder} API to build a {@code SqlSessionFactory}
	 * instance based on an Reader.
	 * 
	 * @return SqlSessionFactory
	 * @throws IOException
	 *             if loading the config file failed
	 * @throws SQLException
	 */
	protected void buildSqlSessionFactory() throws IOException, SQLException {

		Configuration mconfiguration;
		Configuration sconfiguration;

		XMLConfigBuilder mxmlConfigBuilder = null;
		XMLConfigBuilder sxmlConfigBuilder = null;
		if (this.configLocation != null) {
			mxmlConfigBuilder = new XMLConfigBuilder(
					this.configLocation.getInputStream(), null,
					this.configurationProperties);
			sxmlConfigBuilder = new XMLConfigBuilder(
              this.configLocation.getInputStream(), null,
              this.configurationProperties);
			mconfiguration = mxmlConfigBuilder.getConfiguration();
			sconfiguration = sxmlConfigBuilder.getConfiguration();
		} else {
			if (this.logger.isDebugEnabled()) {
				this.logger
						.debug("Property 'configLocation' not specified, using default MyBatis Configuration");
			}
			mconfiguration = new Configuration();
			sconfiguration = new Configuration();
		}

		if (StringUtils.hasLength(this.typeAliasesPackage)) {
			String[] typeAliasPackageArray = StringUtils.tokenizeToStringArray(
					this.typeAliasesPackage,
					ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
			for (String packageToScan : typeAliasPackageArray) {
				mconfiguration.getTypeAliasRegistry().registerAliases(
						packageToScan);
				sconfiguration.getTypeAliasRegistry().registerAliases(
                  packageToScan);
				if (this.logger.isDebugEnabled()) {
					this.logger.debug("Scanned package: '" + packageToScan
							+ "' for aliases");
				}
			}
		}

		if (!ObjectUtils.isEmpty(this.typeAliases)) {
			for (Class<?> typeAlias : this.typeAliases) {
				mconfiguration.getTypeAliasRegistry().registerAlias(typeAlias);
				sconfiguration.getTypeAliasRegistry().registerAlias(typeAlias);
				if (this.logger.isDebugEnabled()) {
					this.logger.debug("Registered type alias: '" + typeAlias
							+ "'");
				}
			}
		}

		if (!ObjectUtils.isEmpty(this.plugins)) {
			for (Interceptor plugin : this.plugins) {
				mconfiguration.addInterceptor(plugin);
				sconfiguration.addInterceptor(plugin);
				if (this.logger.isDebugEnabled()) {
					this.logger.debug("Registered plugin: '" + plugin + "'");
				}
			}
		}

		if (StringUtils.hasLength(this.typeHandlersPackage)) {
			String[] typeHandlersPackageArray = StringUtils
					.tokenizeToStringArray(
							this.typeHandlersPackage,
							ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
			for (String packageToScan : typeHandlersPackageArray) {
				mconfiguration.getTypeHandlerRegistry().register(packageToScan);
				sconfiguration.getTypeHandlerRegistry().register(packageToScan);
				if (this.logger.isDebugEnabled()) {
					this.logger.debug("Scanned package: '" + packageToScan
							+ "' for type handlers");
				}
			}
		}

		if (!ObjectUtils.isEmpty(this.typeHandlers)) {
			for (TypeHandler typeHandler : this.typeHandlers) {
				mconfiguration.getTypeHandlerRegistry().register(typeHandler);
				sconfiguration.getTypeHandlerRegistry().register(typeHandler);
				if (this.logger.isDebugEnabled()) {
					this.logger.debug("Registered type handler: '"
							+ typeHandler + "'");
				}
			}
		}

		if (mxmlConfigBuilder != null) {
			try {
				mxmlConfigBuilder.parse();

				if (this.logger.isDebugEnabled()) {
					this.logger.debug("Parsed configuration file: '"
							+ this.configLocation + "'");
				}
			} catch (Exception ex) {
				throw new NestedIOException("Failed to parse config resource: "
						+ this.configLocation, ex);
			} finally {
				ErrorContext.instance().reset();
			}
		}
		
		if (sxmlConfigBuilder != null) {
          try {
              sxmlConfigBuilder.parse();

              if (this.logger.isDebugEnabled()) {
                  this.logger.debug("Parsed configuration file: '"
                          + this.configLocation + "'");
              }
          } catch (Exception ex) {
              throw new NestedIOException("Failed to parse config resource: "
                      + this.configLocation, ex);
          } finally {
              ErrorContext.instance().reset();
          }
      }

		if (this.transactionFactoryManager == null) {
			transactionFactoryManager = new SummerTransactionManager(
					this.dataSourceManager);
		}

		if (!ObjectUtils.isEmpty(this.mapperLocations)) {
			for (Resource mapperLocation : this.mapperLocations) {
				if (mapperLocation == null) {
					continue;
				}

				// this block is a workaround for issue
				// http://code.google.com/p/mybatis/issues/detail?id=235
				// when running MyBatis 3.0.4. But not always works.
				// Not needed in 3.0.5 and above.
				String path;
				if (mapperLocation instanceof ClassPathResource) {
					path = ((ClassPathResource) mapperLocation).getPath();
				} else {
					path = mapperLocation.toString();
				}

				try {
					XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(
							mapperLocation.getInputStream(), mconfiguration,
							path, mconfiguration.getSqlFragments());
					xmlMapperBuilder.parse();
				} catch (Exception e) {
					throw new NestedIOException(
							"Failed to parse mapping resource: '"
									+ mapperLocation + "'", e);
				} finally {
					ErrorContext.instance().reset();
				}

				if (this.logger.isDebugEnabled()) {
					this.logger.debug("Parsed mapper file: '" + mapperLocation
							+ "'");
				}
			}
		} else {
			if (this.logger.isDebugEnabled()) {
				this.logger
						.debug("Property 'mapperLocations' was not specified or no matching resources found");
			}
		}

		if (null == this.msqlSessionFactory) {
			Environment menvironment = new Environment(this.menvironment,
					this.transactionFactoryManager.getMtransactionFactory(),
					this.dataSourceManager.getmDataSource());
			mconfiguration.setEnvironment(menvironment);

			this.msqlSessionFactory = this.sqlSessionFactoryBuilder
					.build(mconfiguration);
		}

		if (null == this.ssqlSessionFactory) {
			Environment senvironment = new Environment(this.senvironment,
					this.transactionFactoryManager.getStransactionFactory(),
					this.dataSourceManager.getsDataSource());
			sconfiguration.setEnvironment(senvironment);

			this.ssqlSessionFactory = this.sqlSessionFactoryBuilder
					.build(sconfiguration);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public SqlSessionFactory getMObject() throws Exception {
		if (this.msqlSessionFactory == null) {
			afterPropertiesSet();
		}

		return this.msqlSessionFactory;
	}

	/**
	 * {@inheritDoc}
	 */
	public SqlSessionFactory getSObject() throws Exception {
		if (this.ssqlSessionFactory == null) {
			afterPropertiesSet();
		}

		return this.ssqlSessionFactory;
	}

	/**
	 * {@inheritDoc}
	 */
	public Class<? extends SqlSessionFactory> getObjectType() {
		return this.msqlSessionFactory == null ? SqlSessionFactory.class
				: this.msqlSessionFactory.getClass();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isSingleton() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public void onApplicationEvent(ApplicationEvent event) {
		if (failFast && event instanceof ContextRefreshedEvent) {
			// fail-fast -> check all statements are completed
			this.msqlSessionFactory.getConfiguration()
					.getMappedStatementNames();
			this.ssqlSessionFactory.getConfiguration()
					.getMappedStatementNames();
		}
	}

	private SqlSessionFactory getCurrent(){
		try {
			buildSqlSessionFactory();
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		return msqlSessionFactory;
	}
	
	@Override
	public SqlSession openSession() {
		return getCurrent().openSession();
	}

	@Override
	public SqlSession openSession(boolean autoCommit) {
		return getCurrent().openSession(autoCommit);
	}

	@Override
	public SqlSession openSession(Connection connection) {
		// TODO Auto-generated method stub
		return getCurrent().openSession(connection);
	}

	@Override
	public SqlSession openSession(TransactionIsolationLevel level) {
		// TODO Auto-generated method stub
		return getCurrent().openSession(level);
	}

	@Override
	public SqlSession openSession(ExecutorType execType) {
		// TODO Auto-generated method stub
		return getCurrent().openSession(execType);
	}

	@Override
	public SqlSession openSession(ExecutorType execType, boolean autoCommit) {
		// TODO Auto-generated method stub
		return getCurrent().openSession(execType, autoCommit);
	}

	@Override
	public SqlSession openSession(ExecutorType execType,
			TransactionIsolationLevel level) {
		// TODO Auto-generated method stub
		return getCurrent().openSession(execType, level);
	}

	@Override
	public SqlSession openSession(ExecutorType execType, Connection connection) {
		// TODO Auto-generated method stub
		return getCurrent().openSession(execType, connection);
	}

	@Override
	public Configuration getConfiguration() {
		// TODO Auto-generated method stub
		return getCurrent().getConfiguration();
	}
}