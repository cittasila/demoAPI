package com.langying.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;

/**
 * Created by zb on 2015/7/18.
 */

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds=43200)
class SessionConfig {

    @Bean
    HeaderHttpSessionStrategy sessionStrategy() {
       return new HeaderHttpSessionStrategy();
    }
}
