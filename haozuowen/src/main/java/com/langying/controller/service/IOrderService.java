package com.langying.controller.service;

import com.langying.exception.ApiException;
import com.langying.models.TUserOrder;
import org.codehaus.groovy.runtime.dgmimpl.arrays.IntegerArrayGetAtMetaMethod;
import org.springframework.cache.annotation.Cacheable;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by chenxu on 2016/4/7.
 */
public interface IOrderService  extends  IBaseService<TUserOrder>{

    public Map tradeNo(String no);

    Map order(String orderNo,Integer id,String ip,String url) throws ApiException;
    /**
     * 下单请求支付宝支付
     * @param orderNo
     * @param id
     * @param ip
     * @return
     * @throws ApiException
     */
    Map orderOfAlipay(String orderNo, Integer id,String ip)throws ApiException;

    String qrCode(String orderNo,Map data);

    void notify(String orderNo,PrintWriter out,String xml) throws Exception;

    void notifyOfAlipay(String orderNo, HttpServletRequest request,PrintWriter out) throws Exception;

    public Map checkNotFinishOrder() throws Exception;
    public boolean changeUserGold(String orderType,Integer orderId,String status) throws Exception;
}
