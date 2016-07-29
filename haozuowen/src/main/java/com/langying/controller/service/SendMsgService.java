package com.langying.controller.service;


import ch.qos.logback.classic.Logger;
import com.langying.common.contant.CommonConstant;
import com.langying.controller.mapper.UUserMapper;
import com.langying.models.RUserBook;
import com.langying.models.UUser;
import com.langying.toolbox.utils.SmsUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by chenxu on 2016/1/13.
 * 发送短信服务
 */
@Service
public class SendMsgService implements ISendMsgService{

    private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UUserMapper uUserMapper;

    @CachePut(cacheNames ="verificationCode",key="'verificationCode'+#token+''+#mobile+''+#type")
    @Override
    public Map sendMsg(String token,String mobile, String type) {

        String code= CommonConstant.getCode();
        SmsUtil.sendVerificationCode(mobile,code);
        Map map=new HashMap();
        map.put("mobile",mobile);
        map.put("type",type);
        map.put("code",code);
        map.put("time",System.currentTimeMillis());
        logger.info("发送手机"+mobile+"短信验证码:"+code);
        return map;
    }

    /***
     * 从缓存中获取动态码
     * @param token
     * @param mobile
     * @param type
     * @return
     */
    @Cacheable(cacheNames ="verificationCode",key="'verificationCode'+#token+''+#mobile+''+#type")
    @Override
    public Map getMsg(String token,String mobile, String type) {
        return  new HashMap();
    }

    /**
     * 清除随机码
     * @param token
     * @param mobile
     * @param type
     */
    @CacheEvict(cacheNames ="verificationCode",key="'verificationCode'+#token+''+#mobile+''+#type",allEntries=true)
    @Override
    public void clearMsg(String token, String mobile, String type) {

    }

    /***
     *将用户信息和随机码放入缓存
     * @param uuid
     * @param mobile
     * @return
     * @throws Exception
     */
    @Cacheable(cacheNames ="userInfoUuidByMobile",key="'userInfoUuidByMobile'+#uuid")
    @Override
    public UUser userInfoUuidByMobile(String uuid, String mobile) throws Exception {
        UUser uUser=new UUser();
        if(mobile!=null) {
            Map map = CommonConstant.getResMap("userPhone", mobile);
            uUser = uUserMapper.selectByParams(map);
        }
        return uUser;
    }

    /**
     * 清除随机码和用户的关系
     * @param uuid
     */
    @CacheEvict(cacheNames ="userInfoUuidByMobile",key="'userInfoUuidByMobile'+#uuid")
    @Override
    public void clearUserInfoUuid(String uuid) {
    }

    /**
     * 将随机码与验证码放入缓存
     * @param code
     * @param data
     * @return
     */
    @CachePut(cacheNames = "securityCode",key = "'securityCode'+#code")
    public Map putCodeBySecurityCode(String code, Map data) {
        return data;
    }

    /**取出验证码
     * 从缓存中
     * @param code
     * @return
     */
    @Cacheable(cacheNames ="securityCode",key = "'securityCode'+#code")
    public Map securityCodeMap(String code) {
        return null;
    }



    @CacheEvict(cacheNames ="securityCode",key = "'securityCode'+#code")
    public void clearSecurityCode(String code) {

    }


}

