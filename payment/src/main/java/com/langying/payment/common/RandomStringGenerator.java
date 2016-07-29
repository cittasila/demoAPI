package com.langying.payment.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * User: rizenguo
 * Date: 2014/10/29
 * Time: 14:18
 */
public class RandomStringGenerator {

    /**
     * 获取一定长度的随机字符串
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    public static String getNumStringByLength(int length) {
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /***
     * 生成订单
     * 规则：第一位为类型：1支付宝,2微信
     *       14位时间 2位随机数
     * @param type
     * @return
     */
    public static String getNo(String type){
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return type+myFormat.format(new Date())+getNumStringByLength(2);
    }

}
