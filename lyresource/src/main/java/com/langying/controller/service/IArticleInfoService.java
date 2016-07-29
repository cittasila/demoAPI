package com.langying.controller.service;

import com.langying.common.models.Order;
import com.langying.common.models.RollPage;
import com.langying.models.TArticleInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by chenxu on 2016/3/9.
 */
public interface IArticleInfoService  extends  IBaseService<TArticleInfo>{
    /**
     * 查询图书列表
     * @param params
     * @param order
     * @param pageNum
     * @param pageSize
     * @return
     * @throws Exception
     */
    public RollPage findBookInfoListByParams(Map<String, Object> params, Order order, Integer pageNum, Integer pageSize) throws Exception ;

    /**
     * 查询图书id根据主题
     * @param params
     * @param order
     * @return
     * @throws Exception
     */
    public List findBookIdListByParams(Map<String, Object> params, Order order) throws Exception;
}
