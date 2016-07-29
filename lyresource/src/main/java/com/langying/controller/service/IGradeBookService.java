package com.langying.controller.service;

import com.langying.models.TGradeBook;

import java.util.List;
import java.util.Map;

/**
 * Created by Liwei on 2016/5/17.
 */
public interface IGradeBookService {

    /**
     * 查询所有年级和对应的蓝思值
     * @return
     */
    List<TGradeBook> queryAllGrade();

    TGradeBook selectByParams(Map<String,Object> params);

}
