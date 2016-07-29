package com.langying.controller.service;



import com.langying.controller.mapper.UUserProductHistoryMapper;
import com.langying.models.UUserProductHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Liwei on 2016/3/15.
 */
@Service
public class UUserProductHistoryService extends BaseService<UUserProductHistory> implements IUserProductHistoryService{


    private UUserProductHistoryMapper uUserProductHistoryMapper;

    @Autowired
    public void setuUserProductHistoryMapper(UUserProductHistoryMapper uUserProductHistoryMapper) {
        this.uUserProductHistoryMapper = uUserProductHistoryMapper;
    }

    @Override
    public Integer insertUUserProductHistory(UUserProductHistory uUserProductHistory) {
        return uUserProductHistoryMapper.insert(uUserProductHistory);
    }

    @Override
    public <K> K addBasic(UUserProductHistory obj) throws Exception {
        return null;
    }

    @Override
    public void modifyBasic(UUserProductHistory obj) throws Exception {

    }

    @Override
    public void delBasic(UUserProductHistory obj) throws Exception {

    }
}
