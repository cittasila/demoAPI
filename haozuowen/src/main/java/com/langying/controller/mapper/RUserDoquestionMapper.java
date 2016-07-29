package com.langying.controller.mapper;

import com.langying.models.RUserDoquestion;
import com.langying.models.TQuestionOption;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RUserDoquestionMapper extends IOldLangYingMapper<RUserDoquestion>{
    /**
     * 更新记录(有效字段,即非空字段)
     * @param obj
     * @return
     */
    int updateUserDoQuestionActive(RUserDoquestion obj);

    List<RUserDoquestion> selectListWithOptionInfoByParams(@Param(value = "params") Map<String, Object> params,
                                                             @Param(value = "orderParam") String orderParam);
}