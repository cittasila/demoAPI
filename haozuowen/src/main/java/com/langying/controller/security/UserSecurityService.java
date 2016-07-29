package com.langying.controller.security;

import com.langying.controller.mapper.UUserMapper;
import com.langying.exception.ApiException;
import com.langying.handler.PasswordHelper;
import com.langying.models.UUser;
import groovy.transform.TypeChecked;

import java.util.Date;

/**
 * Created by zb on 2015/6/30.
 */

@TypeChecked
public class UserSecurityService{


    private UUserMapper uUserMapper;

    public UserSecurityService(UUserMapper uUserMapper) {
        this.uUserMapper = uUserMapper;
    }

    /**
     * 安全登录
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    UUser loadUserByUsername(String username, String password) throws ApiException {
        if (username==null||username.length()==0) throw new ApiException("9006");
        UUser opUser;
        try{
         opUser=uUserMapper.queryByUserName(username);
        }catch (Exception e){
            throw new ApiException("9007");
        }
        if(opUser==null||!opUser.getUserPwd().equals(PasswordHelper.getPassword(password))){
            throw new ApiException("9007");
        }
        UUser userUpdate=new UUser();
        userUpdate.setUserId(opUser.getUserId());
        userUpdate.setLoginTimes(1);
        userUpdate.setUserLoginDate(new Date());
        uUserMapper.updateByPrimaryKeySelective(userUpdate);
        return opUser;
    }


}
