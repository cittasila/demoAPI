package com.langying.controller.mapper;

import com.langying.models.UUser;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface UUserMapper  extends IOldLangYingMapper<UUser>{
    public UUser queryByUserName(String userName);
    public List<UUser> queryUserListByParam(@Param(value = "params") Map<String, Object> params);
    public List<UUser> queryClassesUserListByParam(@Param(value = "params") Map<String, Object> params);
}