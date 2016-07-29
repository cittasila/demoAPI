package com.langying.controller.resourcemapper;

import com.langying.models.TGradeBook;

import java.util.List;
import java.util.Map;

/**
 * Created by Liwei on 2016/5/17.
 */
public interface TGradeBookMapper extends IResourcesMapper<TGradeBook>{

    /**
     * 查询所有年级和对应的蓝思值
     * @return
     */
    List<TGradeBook> queryAllGrade();



    TGradeBook selectByParams(Map<String, Object> params);


}
