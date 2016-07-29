package com.langying.controller.service;

import com.langying.models.TArticleSentence;

import java.util.List;
import java.util.Map;

/**
 * Created by yucy on 2016/3/17.
 */
public interface IArticleSentenceService extends  IBaseService<TArticleSentence> {
    public List<TArticleSentence> findListByParamsOrderByPartAndSortAsc(Map param);
}
