package com.langying.controller.service;

import com.langying.models.UClasses;

import java.util.List;

/**
 * Created by chenxu on 2016/3/12.
 */
public interface IClassesService  extends IBaseService<UClasses>{
    List<UClasses> selectClassesStudent(Integer teacherId);
}
