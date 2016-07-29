package com.langying.controller.service;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.langying.models.RUserGold;
import com.langying.models.RUserGoldDetail;

/**
 * Created by Liwei on 2016/3/31.
 */
public interface IGoldDetailService  extends IBaseService<RUserGoldDetail>{


    /**
     * 添加一条金币明细记录
     * @param userId 用户 id，记录了是哪个用户的操作
     * @param goldChangeAmount 记录了该明细中金币数量的变化，正数表示增加，负数表示减少
     * @param type 金币发生变化的类型，0 初始化，1用户充值，2用户购书
     * @return
     */
    Integer addUserGoldDetail(RUserGoldDetail rUserGoldDetail);
}
