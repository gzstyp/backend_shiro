package com.yinlz.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
//@MapperScan(basePackages = "com.mzd.multipledatasources.mapper", sqlSessionFactoryRef = "SqlSessionFactory")
public class DataSourceConfig {

    @Primary
	@Bean(name = "dataSourceWrite")
	@ConfigurationProperties(prefix = "spring.datasource.write")
	public DataSource getDateSourceWrite(){
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "dataSourceSlave1")
	@ConfigurationProperties(prefix = "spring.datasource.slave1")
	public DataSource getDateSourceSlave1(){
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "dataSourceSlave2")
	@ConfigurationProperties(prefix = "spring.datasource.slave2")
	public DataSource getDateSourceSlave2(){
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "dynamicDataSource")
	public DynamicDataSource DataSource(@Qualifier("dataSourceWrite") DataSource dataSourceWrite,@Qualifier("dataSourceSlave1") DataSource dataSourceSlave1,@Qualifier("dataSourceSlave2") DataSource dataSourceSlave2) {
		final Map<Object, Object> targetDataSource = new HashMap<>();
		targetDataSource.put(DataSourceType.DataBaseType.Write, dataSourceWrite);
		targetDataSource.put(DataSourceType.DataBaseType.Slave1, dataSourceSlave1);
		targetDataSource.put(DataSourceType.DataBaseType.Slave2, dataSourceSlave2);
		final DynamicDataSource dataSource = new DynamicDataSource();
		dataSource.setTargetDataSources(targetDataSource);
		dataSource.setDefaultTargetDataSource(dataSourceWrite);
		return dataSource;
	}

    @Bean(name = "SqlSessionFactory")
    public SqlSessionFactory test1SqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource) throws Exception{
        final SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dynamicDataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/**.xml"));
        bean.setTypeAliasesPackage("com.yinlz.bean;com.fwtai.bean");//指定包名下的别名
        return bean.getObject();
    }
}