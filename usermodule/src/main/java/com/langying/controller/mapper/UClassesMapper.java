package com.langying.controller.mapper;

import com.langying.models.UClasses;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface UClassesMapper  extends IOldLangYingMapper<UClasses>{
    List<UClasses> selectClassesStudent(@Param(value = "params") Map<String, Object> params);
}