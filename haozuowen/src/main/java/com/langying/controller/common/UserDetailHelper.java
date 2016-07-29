package com.langying.controller.common;

import com.langying.models.UUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by zb on 2015/7/19.
 */
public class UserDetailHelper {

    static public UUser getUserDetail(){
       return  (UUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
