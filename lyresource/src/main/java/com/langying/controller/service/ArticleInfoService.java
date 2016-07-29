package com.langying.controller.service;

import com.langying.common.models.Order;
import com.langying.common.models.RollPage;
import com.langying.controller.resourcemapper.TArticleInfoMapper;
import com.langying.controller.resourcemapper.TArticletopicRelationMapper;
import com.langying.models.TArticleInfo;
import com.langying.toolbox.utils.ClearNullUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by chenxu on 2016/3/9.
 */
@Service
class ArticleInfoService  extends  BaseService<TArticleInfo> implements IArticleInfoService{

    private TArticleInfoMapper mapper;
    @Autowired
    private TArticletopicRelationMapper articletopicRelationMapper;
    @Autowired
    void setMapper(TArticleInfoMapper mapper) {
        this.mapper = mapper;
        super.baseMapper=mapper;
    }


    @Override
    public String addBasic(TArticleInfo obj) throws Exception {
        mapper.insert(obj);
        return obj.getId();
    }

    @Override
    public void modifyBasic(TArticleInfo obj) throws Exception {
        mapper.updateByPrimaryKeySelective(obj);
    }

    @Override
    public void delBasic(TArticleInfo obj) throws Exception {

    }

    @Override
    public RollPage findBookInfoListByParams(Map<String, Object> params, Order order, Integer pageNum, Integer pageSize) throws Exception {
        if(pageSize==null||"".equals(pageSize)){
            pageSize=6;
        }
        RollPage<TArticleInfo> rollPage=this.findListPageByParams(params,order,pageNum,pageSize);
        return rollPage;
     }
    @Override
    public List findBookIdListByParams(Map<String, Object> params, Order order) throws Exception {
        ClearNullUtil.mapClear(params);
        return articletopicRelationMapper.selectListByParams(params,null,null,null);
    }
}
