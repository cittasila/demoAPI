package com.langying.config;

import com.langying.common.cache.ResCodeMessageCache;
import groovy.transform.CompileStatic;
import groovy.transform.TypeChecked;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * Created by zb on 2015/7/30.
 */

@CompileStatic
@TypeChecked
@Configuration
class MessageConfig {

    @Bean
    ResCodeMessageCache loadMessage() throws Exception {
       return new ResCodeMessageCache(new PathMatchingResourcePatternResolver().getResources("classpath:message-resCode.xml"));
    }
}
