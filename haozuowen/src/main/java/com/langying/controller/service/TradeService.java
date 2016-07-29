package com.langying.controller.service;

import com.langying.common.models.Order;
import com.langying.common.models.RollPage;
import com.langying.controller.mapper.RTradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Liwei on 2016/4/20.
 */
@Service
public class TradeService implements ITradeService{

    @Autowired
    private RTradeMapper rTradeMapper;


    @Override
    public RollPage<Map<String,Object>> findGoldRecordByParams(Map<String, Object> params, Integer pageNum, Integer pageSize) {
        Integer recordNum = rTradeMapper.findGoldRecordByParamsCount(params);
        Integer pageOffset = (pageNum-1)*pageSize;
        RollPage<Map<String,Object>> rollPage = new RollPage<>(recordNum,recordNum,pageSize,pageNum);
        rollPage.setResultList(rTradeMapper.findGoldRecordByParams(params,pageOffset,pageSize));
        return rollPage;
    }

    @Override
    public RollPage<Map<String,Object>> findRechargeRecordByParams(Map<String, Object> params, Integer pageNum, Integer pageSize) {
        Integer recordNum = rTradeMapper.findRechargeRecordByParamsCount(params);
        Integer pageOffset = (pageNum-1)*pageSize;
        RollPage<Map<String,Object>> rollPage = new RollPage<>(recordNum,recordNum,pageSize,pageNum);
        rollPage.setResultList(rTradeMapper.findRechargeRecordByParams(params,pageOffset,pageSize));
        return rollPage;
    }
}
