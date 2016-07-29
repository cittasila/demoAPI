package com.langying.controller.mapper;

import com.langying.models.CArticleEvaluateLabel;

public interface CArticleEvaluateLabelMapper extends IOldLangYingMapper<CArticleEvaluateLabel>{
    int insert(CArticleEvaluateLabel record);

    int insertSelective(CArticleEvaluateLabel record);

}