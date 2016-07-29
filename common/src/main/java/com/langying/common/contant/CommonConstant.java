package com.langying.common.contant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by chenxu on 2016/3/14.
 */
public class CommonConstant {
    public static String RE_SUCCESS_CODE="0000";

    public static Map getResultMap(String code, String resMsg){
        Map map=new HashMap();
        map.put("resCode",code);
        map.put("resMsg",resMsg);
        return map;
    }

    /**
     * 将数据装入map里面返回
     * @param object
     * @return
     */
    public static Map getResMap(Object ...object){
        Map map=new HashMap();
        for(int i=0;i<object.length/2;i++){
            map.put(object[2*i],object[2*i+1]);
        }
        return map;
    }
    public static Map getListMap(List list){
        Map map=new HashMap();
        map.put("list",list);
        return map;
    }

    public static String getCode(){
        Random random = new Random();
        String val="";
        val += String.valueOf(random.nextInt(9)+1);
        for(int i=0;i<5;i++){
            val += String.valueOf(random.nextInt(10));
        }
        return val;
    }

    public enum MsgType{
        LoginUpdate("1","注册时验证验证码"),
        SSHZW("2","上海好作文"),
        FindPassword("3","找回密码验证码");
        String desc,value;

        MsgType(String value,String desc){
            this.desc = desc;
            this.value = value;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public enum UserRegisterType{
        SYSTEMIMPORT("0","系统管理员导入（旧版写作管理端导入）"),
        FREEREGISTER("1","自由注册用户"),
        TAOSHIWAN("2","淘师湾用户（参加好作文大赛）"),
        HAOZUOWEN("3","上海好作文用户"),
        SIYECAO("4","四叶草用户"),
        TAOSHIWANSSO("5","淘师湾 SSO 用户"),
        NJYX("6","牛津英雄 用户"),
        SDHAOZUOWEN("7","山东好作文用户"),
        INTER("8","inter注册作文用户");

        private String value;
        private String desc;
        public String getDesc() {
            return desc;
        }
        public void setDesc(String desc) {
            this.desc = desc;
        }
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }
        UserRegisterType(String value,String desc){
            this.value = value;
            this.desc = desc;
        }
    }

    /**
     * 新阅读账号金币明细类型
     */
    public enum UserGoldDetailType{
        INIT("0","用户初始化时由系统自动为每个用户增加金币，金币增加"),
        USERRECHARGE("1","用户充值，金币增加"),
        USERBYBOOK("2","用户购买书本，金币减少");

        private String value;
        private String desc;
        public String getDesc() {
            return desc;
        }
        public void setDesc(String desc) {
            this.desc = desc;
        }
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }
        UserGoldDetailType(String value,String desc){
            this.value = value;
            this.desc = desc;
        }
    }
    public enum OrderType{
        Alipay("1","支付宝"),
        WeiXin("2","微信");

        private String value;
        private String desc;
        public String getDesc() {
            return desc;
        }
        public void setDesc(String desc) {
            this.desc = desc;
        }
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }
        OrderType(String value,String desc){
            this.value = value;
            this.desc = desc;
        }
        public static OrderType getOrderType(String value){
            for(OrderType ot:OrderType.values()){
                if(ot.getValue().equals(value)){
                    return ot;
                }
            }
            return null;
        }
        @Override
        public String toString() {
            return value;
        }
    }


}
