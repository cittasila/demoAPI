package com.langying.controller.service;

import com.langying.models.UUserProductHistory;

/**
 * Created by Liwei on 2016/3/15.
 */
public interface IUserProductHistoryService extends IBaseService<UUserProductHistory>{

    public Integer insertUUserProductHistory(UUserProductHistory uUserProductHistory);
}
