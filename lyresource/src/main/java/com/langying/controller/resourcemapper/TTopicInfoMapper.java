package com.langying.controller.resourcemapper;

import com.langying.models.TTopicInfo;

import java.util.List;
import java.util.Map;


public interface TTopicInfoMapper extends IResourcesMapper<TTopicInfo>{

    /**
     * 根据 topic 的 name 分组查询 topic 下有书本的 topic name 列表（topic 启用，article_status 发布）
     * @param params
     * @return
     */
    List<TTopicInfo> queryTopicList(Map<String,Object> params);
}