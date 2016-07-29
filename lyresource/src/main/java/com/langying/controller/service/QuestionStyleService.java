package com.langying.controller.service;

import com.langying.controller.resourcemapper.TQuestionStyleMapper;
import com.langying.models.TQuestionStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chenxu on 2016/4/19.
 */
@Service
public class QuestionStyleService extends  BaseService<TQuestionStyle> implements IQuestionStyleService{

    private TQuestionStyleMapper questionStyleMapper;

    @Autowired
    public void setQuestionStyleMapper(TQuestionStyleMapper questionStyleMapper) {
        this.questionStyleMapper = questionStyleMapper;
    }

    @Override
    public String addBasic(TQuestionStyle obj) throws Exception {
         questionStyleMapper.insert(obj);
        return obj.getId();
    }

    @Override
    public void modifyBasic(TQuestionStyle obj) throws Exception {
         questionStyleMapper.updateByPrimaryKeySelective(obj);
    }

    @Override
    public void delBasic(TQuestionStyle obj) throws Exception {

    }
}
