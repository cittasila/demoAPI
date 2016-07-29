package com.langying.controller.service;

import com.langying.controller.resourcemapper.TArticleSentenceMapper;
import com.langying.models.TArticleSentence;
import com.langying.toolbox.utils.ClearNullUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by yucy on 2016/3/17.
 */
@Service
public class ArticleSentenceService extends  BaseService<TArticleSentence> implements IArticleSentenceService {

    @Autowired
    private TArticleSentenceMapper articleSentenceMapper;
    @Autowired
    void setMapper(TArticleSentenceMapper articleSentenceMapper) {
        this.articleSentenceMapper = articleSentenceMapper;
        super.baseMapper=articleSentenceMapper;
    }

    @Override
    public <K> K addBasic(TArticleSentence obj) throws Exception {
        return null;
    }

    @Override
    public void modifyBasic(TArticleSentence obj) throws Exception {

    }

    @Override
    public void delBasic(TArticleSentence obj) throws Exception {

    }

    @Override
    public List<TArticleSentence> findListByParamsOrderByPartAndSortAsc(Map param) {
        ClearNullUtil.mapClear(param);
        return articleSentenceMapper.selectListByParamsOrderByPartAndSortAsc(param);
    }
}
