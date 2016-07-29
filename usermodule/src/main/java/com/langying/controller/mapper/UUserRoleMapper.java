package com.langying.controller.mapper;

import com.langying.common.models.LangYingSource;
import com.langying.models.UUserRoleKey;

public interface UUserRoleMapper extends  IOldLangYingMapper<UUserRoleKey> {
    int deleteByPrimaryKey(UUserRoleKey key);

    int insert(UUserRoleKey record);

    int insertSelective(UUserRoleKey record);
}