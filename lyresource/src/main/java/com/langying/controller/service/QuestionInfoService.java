package com.langying.controller.service;

import com.langying.controller.resourcemapper.TQuestionInfoMapper;
import com.langying.models.TQuestionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chenxu on 2016/4/19.
 */
@Service
public class QuestionInfoService  extends  BaseService<TQuestionInfo> implements IQuestionInfoService{

    private TQuestionInfoMapper questionInfoMapper;

    @Autowired
    public void setQuestionInfoMapper(TQuestionInfoMapper questionInfoMapper) {
        this.questionInfoMapper = questionInfoMapper;
        super.baseMapper=questionInfoMapper;
    }

    @Override
    public String addBasic(TQuestionInfo obj) throws Exception {
        questionInfoMapper.insert(obj);
        return obj.getId();
    }

    @Override
    public void modifyBasic(TQuestionInfo obj) throws Exception {
        questionInfoMapper.updateByPrimaryKeySelective(obj);
    }

    @Override
    public void delBasic(TQuestionInfo obj) throws Exception {

    }
}
