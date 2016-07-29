package com.langying.controller.service;

import com.langying.controller.resourcemapper.TQuestionOptionMapper;
import com.langying.models.TQuestionOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chenxu on 2016/4/19.
 */
@Service
public class QuestionOptionService extends  BaseService<TQuestionOption> implements IQuestionOptionService{

   private TQuestionOptionMapper questionOptionMapper;

    @Autowired
    public void setQuestionOptionMapper(TQuestionOptionMapper questionOptionMapper) {
        this.questionOptionMapper = questionOptionMapper;
        super.baseMapper=questionOptionMapper;
    }

    @Override
    public String addBasic(TQuestionOption obj) throws Exception {
        questionOptionMapper.insert(obj);
        return obj.getId();
    }

    @Override
    public void modifyBasic(TQuestionOption obj) throws Exception {
        questionOptionMapper.updateByPrimaryKeySelective(obj);
    }

    @Override
    public void delBasic(TQuestionOption obj) throws Exception {

    }
}
