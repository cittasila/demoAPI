package com.langying.controller.service;

import com.langying.controller.mapper.TUserOrderMapper;
import com.langying.models.TUserOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chenxu on 2016/4/8.
 */
@Service
public class UserOrderService extends BaseService<TUserOrder> implements IUserOrderService{
    private TUserOrderMapper userOrderMapper;
    @Autowired
    public void setUserOrderMapper(TUserOrderMapper userOrderMapper) {
        this.userOrderMapper = userOrderMapper;
        super.baseMapper=userOrderMapper;
    }

    @Override
    public Integer addBasic(TUserOrder obj) throws Exception {
         userOrderMapper.insert(obj);
        return obj.getOrderId();
    }

    @Override
    public void modifyBasic(TUserOrder obj) throws Exception {
        userOrderMapper.updateByPrimaryKeySelective(obj);
    }

    @Override
    public void delBasic(TUserOrder obj) throws Exception {

    }
}
