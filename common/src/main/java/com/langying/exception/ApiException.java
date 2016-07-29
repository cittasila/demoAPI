package com.langying.exception;

import com.langying.common.cache.ResCodeMessageCache;
import com.langying.common.contant.CommonConstant;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import java.lang.reflect.UndeclaredThrowableException;
import java.util.Map;

/**
 * Created by chenxu on 2015/9/21.
 */
public class ApiException extends Exception {

    String resCode;
    String resMsg;
    public ApiException(String resCode) {
        super(ResCodeMessageCache.getMessageDesc(resCode));
        this.resCode=resCode;
        resMsg=ResCodeMessageCache.getMessageDesc(resCode);
    }

    public ApiException(String code, String msg) {
        super(msg);
        this.resCode=code;
        this.resMsg=msg;
    }
    static Map getResPonse(Exception e){

        if(e instanceof UndeclaredThrowableException){
           if((( UndeclaredThrowableException)e).getUndeclaredThrowable() instanceof ApiException){
               ApiException sys=(ApiException)(( UndeclaredThrowableException)e).getUndeclaredThrowable();
               return CommonConstant.getResultMap(sys.resCode,sys.resMsg);
           }
        }else
        if(e instanceof ApiException)
            return CommonConstant.getResultMap(((ApiException) e).resCode,((ApiException) e).resMsg);
        else if(e instanceof UsernameNotFoundException){
            return CommonConstant.getResultMap("0001",e.getMessage());
        }else {
            return CommonConstant.getResultMap("9999",ResCodeMessageCache.getMessageDesc("9999"));
        }
        return null;
    }

}
