package com.langying.controller.resourcemapper;

import com.langying.models.TQuestionOption;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TQuestionOptionMapper extends IResourcesMapper<TQuestionOption>{

    List<TQuestionOption> selectListWithQuestionInfoByParams(@Param(value = "params") Map<String, Object> params,
                              @Param(value = "orderParam") String orderParam);
}