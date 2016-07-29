package com.langying.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.langying.common.cache.RedisCacheManagerExt;
import groovy.transform.CompileStatic;
import groovy.transform.TypeChecked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;



@Configuration
@EnableCaching
@ConfigurationProperties
class RedisConfig extends CachingConfigurerSupport{

    private Map<String,Long> cache=new HashMap<String, Long>();

    public Map<String, Long> getCache() {
        return cache;
    }

    public void setCache(Map<String, Long> cache) {
        this.cache = cache;
    }

    @Autowired
    private Environment env;
    @Bean
    public KeyGenerator wiselyKeyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
               // sb << method.getName()
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
                }
            };
        }

/*    @Bean
    public KeyGenerator paramsKeyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder()
               // sb << target.getClass().getName()
                //sb << method.getName()
                for (Object obj : params) {
                    sb << obj.toString()
                }
                return sb.toString()
            }
        }
    }*/

    @Bean
    public RedisCacheManagerExt cacheManager(
    @SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
        RedisCacheManagerExt cacheManager= new RedisCacheManagerExt(redisTemplate);
        cacheManager.setExpires(cache);
        return cacheManager;
        }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {

        StringRedisTemplate template = new StringRedisTemplate(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

}
