package com.langying.controller.mapper;

import com.langying.models.RUserBook;

public interface RUserBookMapper extends IOldLangYingMapper<RUserBook>{
    int deleteByPrimaryKey(Integer userBookId);

    int insert(RUserBook record);

    int insertSelective(RUserBook record);

    RUserBook selectByPrimaryKey(Integer userBookId);

    int updateByPrimaryKeySelective(RUserBook record);

    int updateByPrimaryKey(RUserBook record);
}