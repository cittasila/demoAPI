package com.langying.controller.mapper;

import com.langying.models.RUserOperateLog;

public interface RUserOperateLogMapper extends IOldLangYingMapper<RUserOperateLog>{
    int deleteByPrimaryKey(Integer id);

    int insert(RUserOperateLog record);

    int insertSelective(RUserOperateLog record);

    RUserOperateLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RUserOperateLog record);

    int updateByPrimaryKey(RUserOperateLog record);
}