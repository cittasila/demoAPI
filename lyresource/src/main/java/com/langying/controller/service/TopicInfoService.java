package com.langying.controller.service;

import com.langying.common.models.Order;
import com.langying.controller.resourcemapper.TArticleInfoMapper;
import com.langying.controller.resourcemapper.TTopicInfoMapper;
import com.langying.models.TArticleInfo;
import com.langying.models.TTopicInfo;
import com.langying.toolbox.utils.ClearNullUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.langying.controller.service.ITopicInfoService;

import java.util.List;
import java.util.Map;

/**
 * Created by thtanghao on 2016/3/16.
 */
@Service
public class TopicInfoService extends  BaseService<TTopicInfo> implements ITopicInfoService{

    private TTopicInfoMapper mapper;

    @Autowired
    public void setMapper(TTopicInfoMapper mapper) {
        this.mapper = mapper;
        super.baseMapper=mapper;
    }
    @Override
    public <K> K addBasic(TTopicInfo obj) throws Exception {
        return null;
    }

    @Override
    public void modifyBasic(TTopicInfo obj) throws Exception {

    }

    @Override
    public void delBasic(TTopicInfo obj) throws Exception {

    }
    @Override
    public List findTopicInfoListByParams(Map<String, Object> params, Order order) throws Exception {
        ClearNullUtil.mapClear(params);
        return mapper.selectListByParams(params,null,null,order==null?null:order.toString());
    }

    @Override
    public List queryTopicList(Map<String, Object> params) {
        ClearNullUtil.mapClear(params);
        return mapper.queryTopicList(params);
    }
}
