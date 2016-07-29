package com.langying.controller.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by chenxu on 2016/3/29.
 */
public interface IRandomCodeService {
    void  createRandomCode(HttpServletRequest request,HttpServletResponse response);
    boolean checkRandomCode(HttpServletRequest request,String inputCode);
}
