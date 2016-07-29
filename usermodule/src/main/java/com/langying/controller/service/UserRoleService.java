package com.langying.controller.service;

import com.langying.controller.mapper.UUserRoleMapper;
import com.langying.models.UUserRoleKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chenxu on 2016/4/15.
 */
@Service
public class UserRoleService extends BaseService<UUserRoleKey> implements IUserRoleService{

    private UUserRoleMapper uUserRoleMapper;

    @Autowired
    public void setuUserRoleMapper(UUserRoleMapper uUserRoleMapper) {
        this.uUserRoleMapper = uUserRoleMapper;
        super.baseMapper=uUserRoleMapper;
    }

    @Override
    public Integer addBasic(UUserRoleKey obj) throws Exception {
        baseMapper.insert(obj);
        return obj.getUserId();
    }

    @Override
    public void modifyBasic(UUserRoleKey obj) throws Exception {

    }

    @Override
    public void delBasic(UUserRoleKey obj) throws Exception {

    }
}
