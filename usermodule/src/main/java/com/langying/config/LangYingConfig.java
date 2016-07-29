package com.langying.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.langying.common.models.LangYingSource;
import groovy.transform.CompileStatic;
import groovy.transform.TypeChecked;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by chenxu on 2016/3/11.
 */
@CompileStatic
@TypeChecked
@EnableTransactionManagement
@Configuration
@MapperScan(basePackages="com.langying.controller.mapper",markerInterface =LangYingSource.class,sqlSessionFactoryRef = "langyingFac" )
class LangYingConfig {
   // static class LangYingConfig1{
        @Autowired
        private Environment env;

        @Primary
        @Bean(destroyMethod="close")
        DataSource dataSource() {
            DruidDataSource dataSource=new DruidDataSource();
            dataSource.setUrl(env.getProperty("mysql.oldlangying.url"));
            dataSource.setUsername(env.getProperty("mysql.oldlangying.username"));
            dataSource.setPassword(env.getProperty("mysql.oldlangying.password"));
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
           return new DataSourceTransactionManager(dataSource());
        }


        @Bean(name = "langyingFac")
        SqlSessionFactory sqlSessionFactory() throws Exception {
            final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
            sessionFactory.setDataSource(dataSource());
            sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*Mapper.xml"));
            return sessionFactory.getObject();
        }
}
