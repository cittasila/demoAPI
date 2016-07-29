package com.langying.controller.service;

import com.langying.common.models.Order;
import com.langying.models.TTopicInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by chenxu on 2016/3/12.
 */
public interface ITopicInfoService extends  IBaseService<TTopicInfo>{

    public List findTopicInfoListByParams(Map<String, Object> params, Order order) throws Exception;

    /**
     * 根据 topic 的 name 分组查询 topic 下有书本的 topic name 列表（topic 启用，article_status 发布）
     * @param params
     * @return
     */
    public List<TTopicInfo> queryTopicList(Map<String,Object> params);

}
