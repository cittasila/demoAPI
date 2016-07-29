package com.langying.controller.service;

import com.langying.controller.mapper.RReadingLogMapper;
import com.langying.controller.mapper.URegisterGradeMapper;
import com.langying.models.URegisterGrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Liwei on 2016/5/23.
 */
@Service
public class URegisterGradeService extends BaseService<URegisterGrade> implements IURegisterGradeService {


    private URegisterGradeMapper uRegisterGradeMapper;

    /**
     * 注意这里的依赖注入
     * @param uRegisterGradeMapper
     */
    @Autowired
    void setMapper(URegisterGradeMapper uRegisterGradeMapper) {
        this.uRegisterGradeMapper = uRegisterGradeMapper;
        super.baseMapper=uRegisterGradeMapper;
    }

    @Override
    public Integer addBasic(URegisterGrade uRegisterGrade) throws Exception {
        uRegisterGradeMapper.insertSelective(uRegisterGrade);
        return uRegisterGrade.getId();
    }

    @Override
    public void modifyBasic(URegisterGrade obj) throws Exception {
        uRegisterGradeMapper.updateByPrimaryKey(obj);
    }

    @Override
    public void delBasic(URegisterGrade obj) throws Exception {
        uRegisterGradeMapper.deleteByPrimaryKey(obj);
    }
}
