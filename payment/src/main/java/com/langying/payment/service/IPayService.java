package com.langying.payment.service;

import com.langying.payment.models.UnifiedOrderData;
import org.springframework.cache.annotation.Cacheable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Map;

/**
 * Created by chenxu on 2016/4/7.
 */
public interface IPayService {


    /**
     * 支付宝统一下单接口
     * @param tradeNo 有氧英语站内订单唯一编号
     * @param subject 商品名称
     * @param totalFee 支付总金额
     * @return
     */
    Map unifiedOrderOfAlipay(String tradeNo,String subject,String totalFee) throws URISyntaxException;


    /***
     * 微信统一下单
     * @param no 订单编号
     * @param detail 订单名称
     * @param totalFee 金额
     * @param timeStart 开始时间
     * @param ip 访问ip地址
     * @param attch 附加数据
     * @param notifyUrl 异步通知地址
     * @return
     * @throws Exception
     */
    Map unifiedOrder(String no, String detail,Integer totalFee,String timeStart, String ip, String attch,String notifyUrl) throws  Exception;
}
