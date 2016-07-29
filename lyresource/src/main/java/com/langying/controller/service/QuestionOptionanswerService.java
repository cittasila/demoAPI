package com.langying.controller.service;

import com.langying.controller.resourcemapper.TQuestionOptionanswerMapper;
import com.langying.models.TQuestionOptionanswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chenxu on 2016/4/19.
 */
@Service
public class QuestionOptionanswerService extends  BaseService<TQuestionOptionanswer> implements IQuestionOptionanswerService{

    private TQuestionOptionanswerMapper questionOptionanswerMapper;

    @Autowired
    public void setQuestionOptionanswerMapper(TQuestionOptionanswerMapper questionOptionanswerMapper) {
        this.questionOptionanswerMapper = questionOptionanswerMapper;
        super.baseMapper=questionOptionanswerMapper;
    }

    @Override
    public String addBasic(TQuestionOptionanswer obj) throws Exception {
        questionOptionanswerMapper.insert(obj);
        return obj.getId();
    }

    @Override
    public void modifyBasic(TQuestionOptionanswer obj) throws Exception {
        questionOptionanswerMapper.updateByPrimaryKeySelective(obj);
    }

    @Override
    public void delBasic(TQuestionOptionanswer obj) throws Exception {

    }
}
