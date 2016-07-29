package com.langying.controller.mapper;

import com.langying.models.UUserProductHistory;

public interface UUserProductHistoryMapper extends IOldLangYingMapper<UUserProductHistory>{
    int insert(UUserProductHistory record);

    int insertSelective(UUserProductHistory record);
}