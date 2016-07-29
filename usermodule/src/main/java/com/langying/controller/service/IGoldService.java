package com.langying.controller.service;

import com.langying.models.RUserGold;
import com.langying.models.UClasses;
import com.langying.models.UUser;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Created by Liwei on 2016/3/31.
 */
public interface IGoldService extends IBaseService<RUserGold>{


    /**
     *
     * @param userId
     * @param initGoldNum
     * @return
     */
    Boolean initGoldAndDetail(Integer userId,Integer initGoldNum);


    /**
     * 根据 条件返回记录
     * @param params
     * @return
     */
    RUserGold selectByParams(@Param(value = "params") Map<String, Object> params);

}
