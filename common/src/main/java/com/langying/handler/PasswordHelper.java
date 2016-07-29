package com.langying.handler;


import com.langying.toolbox.security.Security;

/**
 * Created with IntelliJ IDEA.
 */
public class PasswordHelper {


    /**
     * 密码加密
     * @param password
     * @return
     */
    public static String getPassword(String password){
        String pw = "L"+password+"οY";
        return Security.MD5(pw);
    }



}
