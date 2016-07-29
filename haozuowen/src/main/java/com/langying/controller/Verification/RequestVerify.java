package com.langying.controller.Verification;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.langying.exception.ApiException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by chenxu on 2016/1/14.
 */
public class RequestVerify {
    public static  Map<String,Map> comMap() throws IOException {
        String json= "{\"loginName\":{\"maxLength\":30,\"rdesc\":\"登录名\"}," +
                "\"realname\":{\"maxLength\":20,\"rdesc\":\"真实姓名\"}," +
                "\"userName\":{\"minLength\":6,\"maxLength\":30,\"rdesc\":\"用户名\"}," +
                "\"userRealName\":{\"maxLength\":20,\"rdesc\":\"姓名\"}," +
                "\"newPassword\":{\"maxLength\":30,\"rdesc\":\"新密码\"},"+
                "\"password\":{\"minLength\":6,\"maxLength\":30,\"rdesc\":\"密码\"},"+
                "\"userParentName\":{\"maxLength\":30,\"rdesc\":\"家长姓名\"}," +
                "\"email\":{\"maxLength\":30,\"rdesc\":\"email\",\"pattern\":\"^[\\\\w-]+(\\\\.[\\\\w-]+)*@[\\\\w-]+(\\\\.[\\\\w-]+)+$\",\"patternMsg\":\"email格式错误\"}," +
                "\"userEmail\":{\"maxLength\":30,\"rdesc\":\"email\",\"pattern\":\"^[\\\\w-]+(\\\\.[\\\\w-]+)*@[\\\\w-]+(\\\\.[\\\\w-]+)+$\",\"patternMsg\":\"email格式错误\"}," +
                "\"teacherName\":{\"maxLength\":50,\"rdesc\":\"教师姓名\"}," +
                "\"mobile\":{\"pattern\":\"^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$\",\"patternMsg\":\"手机号格式有误,请重新输入\"}," +
                "\"userPhone\":{\"pattern\":\"^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$\",\"patternMsg\":\"手机号格式有误,请重新输入\"}," +
                "\"userParentPhone\":{\"pattern\":\"^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$\",\"patternMsg\":\"家长手机号格式有误,请重新输入\"}" +
                "}";
        Map<String,Map> map=new ObjectMapper().readValue(json,Map.class);
        return map;
    }

    /**
     * 校验参数格式
     * @param request
     * @throws Exception
     */
    public  static void verify(HttpServletRequest request) throws Exception {

       /* Map map=[loginName  :[maxLength:30, rdesc:"登录名"],
                 realname   :[maxLength:30,rdesc:"真实姓名"],
                 email      :[pattern: '^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$', patternMsg: 'email格式错误'],
                 teacherName:[maxLength:50,rdesc:"教师姓名"],
                 mobile     :[ pattern: '^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$', patternMsg: "手机号格式有误,请重新输入"]]*/
        Map<String,Map> map=comMap();
        Map param=request.getParameterMap();
        for(Object k:param.keySet()){
            if(map.containsKey(k.toString())){
                if(map.get(k)!=null&&(map.get(k).toString()).length()>0){
                   String value=request.getParameter(k.toString());

                   Map verifyMap=map.get(k.toString());
                    String msg=verifyMap.containsKey("rdesc")?verifyMap.get("rdesc").toString():k.toString();
                   if(verifyMap.containsKey("maxLength")&&value.getBytes("GBK").length>Integer.parseInt(verifyMap.get("maxLength").toString())) {
                       throw new ApiException("2001", String.format("%s最大输入%s字符,请重新输入", msg,verifyMap.get("maxLength")));
                   }
                     if(verifyMap.containsKey("minLength")&&value.getBytes("GBK").length<Integer.parseInt(verifyMap.get("minLength").toString())) {
                        throw new ApiException("2001", String.format("%s最小输入%s字符,请重新输入", msg,verifyMap.get("minLength")));
                    }
                    if(verifyMap.containsKey("pattern")&& value.length()!=0){
                       if(!Pattern.matches(verifyMap.get("pattern").toString(),value))
                           throw new ApiException("2001",verifyMap.get("patternMsg").toString());
                   }
                }
            }
        }
    }

}
