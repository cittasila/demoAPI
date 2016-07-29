package com.langying.config;

import groovy.transform.CompileStatic;
import groovy.transform.TypeChecked;
import org.springframework.context.annotation.Configuration;

/**
 * 数据库连接池配置
 */
@CompileStatic
@TypeChecked
@Configuration
class DataSourceConfig {



   /* @MapperScan(basePackages="com.langying.controller.resourcemapper.lyresource",markerInterface = ReadingSource.class,sqlSessionFactoryRef = "readingSessionFactory")
    static class readingConfig{

        @Autowired
        private Environment env
        @Bean(destroyMethod="close")
        DataSource dataSource() {
            new DruidDataSource(
                    url:env.getProperty("mysql.lyresource.url"),
                    username : env.getProperty("mysql.lyresource.username"),
                    password : env.getProperty("mysql.lyresource.password"),
                    maxActive : env.getProperty("mysql.maxActive") as int,
                    initialSize : 0,
                    minIdle : 0,
                    maxWait : 60000l,
                    validationQuery : "SELECT 1",
                    testOnBorrow : true,
                    testOnReturn : true,
                    testWhileIdle : true,
                    timeBetweenEvictionRunsMillis : 60000l,
                    minEvictableIdleTimeMillis : 25200000l,
                    removeAbandoned : true,
                    removeAbandonedTimeout : 1800,
                    logAbandoned : true
            )
        }

        @Bean
        DataSourceTransactionManager transactionManager() {
            new DataSourceTransactionManager(dataSource())
        }

        @Bean(name="readingSessionFactory")
        SqlSessionFactory sqlSessionFactory() throws Exception {
            final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean(
                    dataSource: dataSource(),
                    mapperLocations:new PathMatchingResourcePatternResolver().getResources("classpath*:resourcemapper/lyresource*//*Mapper.xml")
            )
            return sessionFactory.getObject()
        }
    }*/
}
