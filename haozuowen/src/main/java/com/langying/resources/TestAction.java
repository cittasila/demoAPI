package com.langying.resources;

import com.langying.common.contant.CommonConstant;
import com.langying.handler.ResponseHandler;
import com.langying.toolbox.utils.DateUtil;
import com.langying.toolbox.utils.MD5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by chenxu on 2016/4/27.
 */
@RestController
@Api("测试")
@RequestMapping("/test")
public class TestAction {
    @RequestMapping(value = "lyTaskLoginParam",method = RequestMethod.GET)
    @ResponseBody
    public ResponseHandler lyTaskLoginParam(@ApiParam("登录名") @RequestParam(value = "loginName", required = true) String loginName,
                                            @ApiParam("密码") @RequestParam(value = "password", required = true) String password)
            throws Exception{
        String postTime= DateUtil.formatDate(new Date(),"yyyyMMddHHmmss");
        String miyao="tokjbhw65n46qptxci";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());
        String checkString = date + postTime + loginName+password + miyao;
        String pkey=MD5Util.getMD5Str(checkString);
        return ResponseHandler.getResponse(CommonConstant.getResMap("postTime",postTime,"pkey",pkey));
    }
}
