package com.langying.payment.common.alipay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by Liwei on 2016/4/12.
 */
public class AlipaySubmit {

    private static final Logger logger = LoggerFactory.getLogger(AlipaySubmit.class);

    /**
     * 入口函数
     * 生成要请求给支付宝的参数数组
     * @param sParaTemp 请求前的参数数组
     * @return 要请求的参数数组
     */
    public static Map<String, String> buildRequestPara(Map<String, String> sParaTemp) {
        // 除去数组中的空值和签名参数

        // 去掉 key的名字是 “sign” 和 “sign_type” 和 值为 null 或者 “” 的请求参数
        Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
        //生成签名结果，放入请求参数 “sign” 中
        String mysign = buildRequestMysign(sPara);

        //签名结果与签名方式加入请求提交参数组中
        sPara.put("sign", mysign);
        sPara.put("sign_type", AlipayConfig.sign_type);

        return sPara;
    }


    /**
     * 生成签名结果
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestMysign(Map<String, String> sPara) {
        /**
         * 按照 key 的名字的 ASCII 码递增排序
         */
        String prestr = AlipayCore.createLinkString(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        logger.debug("加密前拼接的字符串 prestr => " + prestr);
        String mysign = "";
        if(AlipayConfig.sign_type.equals("MD5") ) {
            mysign = MD5.sign(prestr, AlipayConfig.key, AlipayConfig._input_charset);
        }
        logger.debug("加密后的字符串 mysign => " + mysign);
        return mysign;
    }
}
