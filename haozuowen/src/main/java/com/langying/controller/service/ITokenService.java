package com.langying.controller.service;

import com.langying.models.UUser;

import java.util.Map;

/**
 * Created by chenxu on 2016/4/15.
 * 保存token相关信息
 */
public interface ITokenService {
    public Map tokenUser(String token, UUser uUser);
    public Map tokenUser(String token);
}
