package com.langying.payment.common.alipay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

/**
 * Created by Liwei on 2016/4/12.
 */
@Configuration
public class AlipayConfig {

    @Autowired
    private Environment environment;

    @PostConstruct
    private void init(){
        partner = environment.getProperty("alipay.partner");
        seller_id = environment.getProperty("alipay.sellerId");
        notify_url = environment.getProperty("alipay.notify.url");
        return_url = environment.getProperty("alipay.return.url");
        key = environment.getProperty("alipay.key");
    }

    // 调用的接口名，无需修改
    public static String service = "create_direct_pay_by_user";

    /**
     * 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
     * 合作者身份ID，签约账号，当你成为支付宝的签约用户时会分配一个以2088开头由16位纯数字组成的字符串，查看地址：b.alipay.com->我的商家服务->查看PID、Key。
     */
    public static String partner;

    // 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
    public static String seller_id;

    // 字符编码格式 目前支持 gbk 或 utf-8
    public static String _input_charset = "utf-8";

    // 支付类型 ，无需修改
    public static String payment_type = "1";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 程序员根据业务逻辑自定义
    // 支付结果以异步通知为准
    public static String notify_url;

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 程序员根据业务逻辑自定义
    // 同步跳转地址，目前地址取自配置文件
    public static String return_url;

    // 签名方式（我们公司使用的是 MD5 加密方式，选择了该方式就需要登录支付宝网站查看安全校验码，安全校验码填写在下面这个参数 key ）
    public static String sign_type = "MD5";

    // MD5密钥，安全检验码，由数字和字母组成的32位字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
    public static String key;

}
