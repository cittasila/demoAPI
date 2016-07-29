package com.langying.controller.service;

import com.langying.models.UDistcd;

import java.util.List;
import java.util.Map;

/**
 * 常用服务
 * Created by chenxu on 2016/3/15.
 */
public interface ICommonService {
    /**
     * 获取城市列表
     * @param code
     * @return
     */
    public List<Map> getArea(String code);

    /**
     * 通过区域代码获取学校列表
     * @param code
     * @return
     */
    public List<Map> getSchool(String code);
}
