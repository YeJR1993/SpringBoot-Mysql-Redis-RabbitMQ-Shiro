package com.shanghai.common.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author YeJR
 * @version: 2018年4月28日 上午10:07:51
 * 使用此方式才能在配置文件中支持Druid连接池的属性
 * @Configuration : spring boot配置文件注解，被扫描为bean文件
 * @ConditionalOnClass: 存在Druid jar
 * @ConditionalOnProperty: 在application.yml配置文件中存在相应的name-value继续执行
 */
@Configuration
public class DruidConfig {
	
	/**存在Druid jar*/
	@ConditionalOnClass(DruidDataSource.class)
	/**在application.yml配置文件中存在相应的name-value继续执行*/
	@ConditionalOnProperty(name = "spring.datasource.type", havingValue = "com.alibaba.druid.pool.DruidDataSource", matchIfMissing = true)
	static class Druid extends DruidConfig {
		@Bean
		@ConfigurationProperties("spring.datasource.druid")
		public DruidDataSource dataSource(DataSourceProperties properties) {
			DruidDataSource druidDataSource = (DruidDataSource) properties.initializeDataSourceBuilder().type(DruidDataSource.class).build();
			DatabaseDriver databaseDriver = DatabaseDriver.fromJdbcUrl(properties.determineUrl());
			String validationQuery = databaseDriver.getValidationQuery();
			if (StringUtils.isNotBlank(validationQuery)) {
				druidDataSource.setValidationQuery(validationQuery);
			}
			return druidDataSource;
		}
	}
}
