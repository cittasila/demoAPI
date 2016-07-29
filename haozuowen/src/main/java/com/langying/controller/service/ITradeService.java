package com.langying.controller.service;

import com.langying.common.models.Order;
import com.langying.common.models.RollPage;

import java.util.List;
import java.util.Map;

/**
 * Created by Liwei on 2016/4/19.
 */
public interface ITradeService {

    /**
     * 查询金币消耗记录
     * @param params
     * @param pageNum
     * @param pageSize
     * @return
     */
    RollPage<Map<String,Object>> findGoldRecordByParams(Map<String, Object> params, Integer pageNum, Integer pageSize);


    /**
     * 查询充值交易记录
     * @param params
     * @param pageNum
     * @param pageSize
     * @return
     */
    RollPage<Map<String,Object>> findRechargeRecordByParams(Map<String, Object> params, Integer pageNum, Integer pageSize);


}
