package com.langying.controller.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.langying.models.UUser;
import com.langying.toolbox.utils.BeanUtil;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by chenxu on 2016/4/15.
 */
@Service
public class TokenService implements ITokenService{

    /**
     * 设置token与用户的关系供其他应用使用
     * @param token
     * @param uUser
     * @return
     */
    @CachePut(cacheNames ="token",key="'token'+#token")
    @Override
    public Map tokenUser(String token, UUser uUser) {
        return BeanUtil.transBean2Map(uUser);
    }

    @Cacheable(cacheNames ="token",key="'token'+#token")
    @Override
    public Map tokenUser(String token) {
        return null;
    }
}
