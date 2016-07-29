package com.langying.controller.service;

import com.langying.common.contant.CommonConstant;
import com.langying.controller.mapper.CArticleEvaluateLabelMapper;
import com.langying.models.CArticleEvaluateLabel;
import com.langying.models.UClasses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yucy on 2016/3/16.
 */
@Service
public class ArticleEvaluateLabelService extends BaseService<CArticleEvaluateLabel> implements  IArticleEvaluateLabelService {

    @Autowired
    CArticleEvaluateLabelMapper articleEvaluateLabelMapper;
    @Autowired
    void setcArticleEvaluateLabelMapper(CArticleEvaluateLabelMapper articleEvaluateLabelMapper) {
        this.articleEvaluateLabelMapper = articleEvaluateLabelMapper;
        super.baseMapper=articleEvaluateLabelMapper;
    }

    @Override
    public List<CArticleEvaluateLabel> getCommentLabelList() {
        List<CArticleEvaluateLabel> labelList=new ArrayList<CArticleEvaluateLabel>();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("isActive", "1");
        labelList=articleEvaluateLabelMapper.selectListByParams(map,null,null,null);
        return labelList;
    }

    @Override
    public <K> K addBasic(CArticleEvaluateLabel obj) throws Exception {
        return null;
    }

    @Override
    public void modifyBasic(CArticleEvaluateLabel obj) throws Exception {

    }

    @Override
    public void delBasic(CArticleEvaluateLabel obj) throws Exception {

    }
}
