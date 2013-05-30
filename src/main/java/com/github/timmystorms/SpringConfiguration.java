package com.github.timmystorms;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jolbox.bonecp.BoneCPDataSource;

@Configuration
@EnableTransactionManagement
@PropertySource({ "classpath:mysql.properties" })
@ComponentScan({ "com.github.timmystorms" })
@EnableJpaRepositories(basePackages = { "com.github.timmystorms" })
@ImportResource("classpath:config.xml")
public class SpringConfiguration extends RepositoryRestMvcConfiguration {

	@Autowired
	private Environment env;

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		final LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setDataSource(dataSource());
		entityManager.setPersistenceUnitName("spring-jpa");
		final HibernateJpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
		jpaAdapter.setDatabase(Database.MYSQL);
		jpaAdapter.setShowSql(true);
		jpaAdapter.setGenerateDdl(true);
		entityManager.setJpaVendorAdapter(jpaAdapter);
		return entityManager;
	}

	@Bean
	public LazyConnectionDataSourceProxy dataSource() {
		return new LazyConnectionDataSourceProxy(pooledDataSource());
	}

	@Bean(destroyMethod = "close")
	public DataSource pooledDataSource() {
		final BoneCPDataSource dataSource = new BoneCPDataSource();
		dataSource.setDriverClass(env.getProperty("jdbc.driverClassName"));
		dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
		dataSource.setUsername(env.getProperty("jdbc.user"));
		dataSource.setPassword(env.getProperty("jdbc.pass"));
		// <property name="idleConnectionTestPeriod" value="60"/>
		// <property name="idleMaxAge" value="240"/>
		// <property name="maxConnectionsPerPartition" value="30"/>
		// <property name="minConnectionsPerPartition" value="10"/>
		// <property name="partitionCount" value="3"/>
		// <property name="acquireIncrement" value="5"/>
		// <property name="statementsCacheSize" value="100"/>
		// <property name="releaseHelperThreads" value="3"/>
		return dataSource;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		final JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return txManager;
	}

}
