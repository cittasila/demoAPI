package com.langying.controller.service;

import com.langying.models.CArticleEvaluateLabel;

import java.util.List;
import java.util.Map;

/**
 * Created by yucy on 2016/3/16.
 */
public interface IArticleEvaluateLabelService extends IBaseService<CArticleEvaluateLabel>{
    public List<CArticleEvaluateLabel> getCommentLabelList();
}
