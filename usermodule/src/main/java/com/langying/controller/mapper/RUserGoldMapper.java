package com.langying.controller.mapper;

import com.langying.models.RUserGold;

public interface RUserGoldMapper extends IOldLangYingMapper<RUserGold>{

    int deleteByPrimaryKey(Integer userGoldId);

    int insert(RUserGold record);

    int insertSelective(RUserGold record);

    RUserGold selectByPrimaryKey(Integer userGoldId);

    int updateByPrimaryKeySelective(RUserGold record);

    int updateByPrimaryKey(RUserGold record);
}