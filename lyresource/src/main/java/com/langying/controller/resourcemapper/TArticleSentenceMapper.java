package com.langying.controller.resourcemapper;

import com.langying.models.TArticleSentence;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface TArticleSentenceMapper extends IResourcesMapper<TArticleSentence>{
    List<TArticleSentence> selectListByParamsOrderByPartAndSortAsc(@Param(value = "params") Map<String, Object> params);
}