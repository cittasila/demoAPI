package com.langying.payment.service;

import com.langying.payment.common.Configure;
import com.langying.payment.common.HttpsRequest;
import com.langying.payment.common.PojoMapConverter;
import com.langying.payment.common.alipay.AlipayConfig;
import com.langying.payment.common.alipay.AlipayCore;
import com.langying.payment.common.alipay.AlipaySubmit;
import com.langying.payment.models.UnifiedOrderData;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付
 * Created by chenxu on 2016/4/7.
 */
@Service
public class PayService implements IPayService{

    // 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
    private static String log_path = File.pathSeparator + "hzy" + File.separator + "liwei_alipay_log" + File.separator;

    // 支付宝【即时到帐】在线开发文档
    // https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.O9yorI&treeId=62&articleId=103740&docType=1

    /**
     *
     * @param tradeNo 有氧英语站内订单唯一编号
     * @param subject 商品名称
     * @param totalFee
     * @return
     */
    @Override
    public Map unifiedOrderOfAlipay(String tradeNo,String subject,String totalFee) throws URISyntaxException {
        Map<String,String> map = new HashMap<>();
        String body = "有氧英语金币用于购买英语阅读书本， 1 本书目前定价 10 金币。金币与人民币的兑换比率为：10 金币 = 1 元人民币。";
        // 把请求参数打包成数组
        Map<String,String> sParaTemp = new HashMap<>();
        // 调用的接口名称
        sParaTemp.put("service", AlipayConfig.service);
        // 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，
        sParaTemp.put("partner", AlipayConfig.partner);
        // 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
        sParaTemp.put("seller_id", AlipayConfig.seller_id);
        // 字符编码格式 utf-8
        sParaTemp.put("_input_charset", AlipayConfig._input_charset);
        // 1
        sParaTemp.put("payment_type", AlipayConfig.payment_type);
        // 服务器异步通知页面路径
        sParaTemp.put("notify_url", AlipayConfig.notify_url + tradeNo);
        // 页面跳转同步通知页面路径
        sParaTemp.put("return_url", AlipayConfig.return_url);
        // 请求参数 1：商户订单号，商户网站订单系统中唯一订单号，必填
        sParaTemp.put("out_trade_no", tradeNo);
        // 请求参数 2：订单名称，必填
        sParaTemp.put("subject", subject);
        // 请求参数 3：付款金额，必填
        sParaTemp.put("total_fee", totalFee);
        // 请求参数 4：商品描述，可空
        sParaTemp.put("body", body);
        //待请求参数数组
        Map<String, String> sPara = AlipaySubmit.buildRequestPara(sParaTemp);
        // GET 方式
        URIBuilder uriBuilder = new URIBuilder().setScheme("https")
                .setHost("mapi.alipay.com")
                .setPath("/gateway.do");
        for(Map.Entry<String,String> entry:sPara.entrySet()){
            uriBuilder = uriBuilder.setParameter(entry.getKey(),entry.getValue());
        }
        HttpGet httpget = new HttpGet(uriBuilder.build());
        String result_url = httpget.getURI().toString();
        map.put("result_code","SUCCESS");
        map.put("result_url",result_url);
        return map;
    }

    /**
     * 微信下单
     * @param no 订单编号
     * @param detail 商品描述
     * @param totalFee 金额
     * @param timeStart 开始时间
     * @param ip ip地址
     * @param attch 附加数据
     * @param notifyUrl 异步通知地址
     * @return
     * @throws Exception
     */
    @Override
    public Map unifiedOrder(String no, String detail,Integer totalFee,String timeStart, String ip, String attch,String notifyUrl) throws Exception {
        UnifiedOrderData unifiedOrderData=new UnifiedOrderData(no,detail,totalFee,ip,timeStart,attch, notifyUrl);
        HttpsRequest request=new HttpsRequest();
        String xml=request.sendXmlPost(Configure.UnifiedOrder,unifiedOrderData);
        Map<String,Object> map=new HashMap();
        if(xml!=null){
            XStream xStream = new XStream(new DomDriver());
            xStream.registerConverter(new PojoMapConverter());
             map= (Map<String,Object>)xStream.fromXML(xml.replaceAll("<xml>","<map>").replaceAll("</xml>","</map>"));
            map.put("xml",xml);
        }
        return map;
    }
}
