package com.langying.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Created by chenxu on 2016/1/4.
 */
@Configuration
@ConfigurationProperties
public class DomainConfig {
     List<String> domain;
}
