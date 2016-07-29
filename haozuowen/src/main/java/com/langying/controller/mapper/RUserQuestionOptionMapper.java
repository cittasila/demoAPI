package com.langying.controller.mapper;

import com.langying.models.RUserQuestionOption;
import io.swagger.models.auth.In;

import java.util.Map;

public interface RUserQuestionOptionMapper extends IOldLangYingMapper<RUserQuestionOption>{

    Integer queryUserFinishedRightCount(Map<String,Object> params);

}