package com.langying.controller.service;

import com.langying.exception.ApiException;

/**
 * Created by chenxu on 2016/3/16.
 */
public interface IUserManageService {
    /**
     * 处理数据列表
     * @param excelResult
     * @param roleId
     * @param schoolId
     * @param oprUser
     * @throws ApiException
     */
    public int dealUser(String[][] excelResult,String roleId,Integer schoolId,String oprUser,String userType) throws ApiException;
}
