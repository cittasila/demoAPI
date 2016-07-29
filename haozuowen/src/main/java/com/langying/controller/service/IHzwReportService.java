package com.langying.controller.service;

import com.langying.exception.ApiException;
import com.langying.models.UUser;
import org.springframework.cache.annotation.CachePut;

import java.util.List;
import java.util.Map;

/**
 * Created by chenxu on 2016/3/21.
 */
public interface IHzwReportService {

    /***
     * 通过用户查找文章
     * @param token
     * @param mobil
     * @param userName
     * @return
     */

    public Map essayList(String token,String mobil,String userName) throws ApiException, ApiException;


    public Map getEssayList(String token);


    public Map report(Integer assignmentId,UUser user);

}
