package com.langying.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.langying.common.models.ReadingSource;
import groovy.transform.CompileStatic;
import groovy.transform.TypeChecked;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 数据库连接池配置
 */
@CompileStatic
@TypeChecked
@Configuration
@MapperScan(basePackages="com.langying.controller.resourcemapper",markerInterface = ReadingSource.class,sqlSessionFactoryRef = "resourceSessionFactory")
  class ResourceDataSourceConfig {


        @Autowired
        private Environment env;

        @Bean(destroyMethod = "close")
        DataSource lyresourceDataSource() {
            DruidDataSource dataSource=new DruidDataSource();
            dataSource.setUrl(env.getProperty("mysql.lyresource.url"));
            dataSource.setUsername(env.getProperty("mysql.lyresource.username"));
            dataSource.setPassword(env.getProperty("mysql.lyresource.password"));
            dataSource.setMaxActive(Integer.parseInt(env.getProperty("mysql.maxActive")));
            dataSource.setInitialSize(0);
            dataSource.setValidationQuery( "SELECT 1");
            dataSource.setTestOnBorrow( true);
            dataSource.setTestOnReturn( true);
            dataSource.setTestWhileIdle( true);
            dataSource.setTimeBetweenEvictionRunsMillis( 60000l);
            dataSource.setMinEvictableIdleTimeMillis( 25200000l);
            dataSource.setRemoveAbandoned( true);
            dataSource.setRemoveAbandonedTimeout( 1800);
            dataSource.setLogAbandoned( true);
            return dataSource;
        }

        @Bean
        DataSourceTransactionManager transactionManager() {
            return new DataSourceTransactionManager(lyresourceDataSource());
        }

        @Bean(name = "resourceSessionFactory")
        SqlSessionFactory sqlSessionFactory() throws Exception {
            final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
            sessionFactory.setDataSource(lyresourceDataSource());
            sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/lyresource/*Mapper.xml"));
            return sessionFactory.getObject();
        }

}