package com.langying.controller.service;

import com.langying.common.contant.CommonConstant;
import com.langying.controller.mapper.RUserGoldDetailMapper;
import com.langying.models.RUserGoldDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Liwei on 2016/3/31.
 */
@Service
public class GoldDetailService extends BaseService<RUserGoldDetail> implements IGoldDetailService {


    private RUserGoldDetailMapper rUserGoldDetailMapper;
    @Autowired
    public void setrUserGoldDetailMapper(RUserGoldDetailMapper rUserGoldDetailMapper) {
        this.rUserGoldDetailMapper = rUserGoldDetailMapper;
        super.baseMapper=rUserGoldDetailMapper;
    }

    @Override
    public Integer addUserGoldDetail(RUserGoldDetail rUserGoldDetail) {
        Integer insertNum = 0;
        try {
            // 返回插入数据的条数
            insertNum = rUserGoldDetailMapper.insertSelective(rUserGoldDetail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return insertNum;
    }

    @Override
    public Integer addBasic(RUserGoldDetail obj) throws Exception {
        obj.setCreateTime(new Date());
        obj.setIsActive("1");
        baseMapper.insert(obj);
        return obj.getUserGoldDetailId();
    }

    @Override
    public void modifyBasic(RUserGoldDetail obj) throws Exception {
        baseMapper.updateByPrimaryKeySelective(obj);
    }

    @Override
    public void delBasic(RUserGoldDetail obj) throws Exception {

    }
}
