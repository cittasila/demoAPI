package com.langying.controller.service;

import com.langying.controller.resourcemapper.TGradeBookMapper;
import com.langying.models.TGradeBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Liwei on 2016/5/17.
 */
@Service
public class GradeBookService implements IGradeBookService {

    @Autowired
    private TGradeBookMapper tGradeBookMapper;

    @Override
    public List<TGradeBook> queryAllGrade() {
        return tGradeBookMapper.queryAllGrade();
    }

    @Override
    public TGradeBook selectByParams(Map<String, Object> params) {
        return tGradeBookMapper.selectByParams(params);
    }
}
