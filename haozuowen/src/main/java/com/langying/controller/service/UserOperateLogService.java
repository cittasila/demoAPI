package com.langying.controller.service;

import com.langying.controller.mapper.RUserOperateLogMapper;
import com.langying.models.RUserOperateLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yucy on 2016/3/17.
 */
@Service
public class UserOperateLogService extends BaseService<RUserOperateLog> implements IUserOperateLogService {

    @Autowired
    RUserOperateLogMapper userOperateLogMapper;

    @Autowired
    void setrUserBookMapper(RUserOperateLogMapper userOperateLogMapper) {
        this.userOperateLogMapper = userOperateLogMapper;
        super.baseMapper=userOperateLogMapper;
    }

    @Override
    public Integer addBasic(RUserOperateLog obj) throws Exception {
        userOperateLogMapper.insert(obj);
        return obj.getId();
    }

    @Override
    public void modifyBasic(RUserOperateLog obj) throws Exception {

    }

    @Override
    public void delBasic(RUserOperateLog obj) throws Exception {

    }
}
