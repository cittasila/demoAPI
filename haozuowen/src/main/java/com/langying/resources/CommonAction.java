package com.langying.resources;

import com.langying.common.contant.CommonConstant;
import com.langying.controller.Verification.RequestVerify;
import com.langying.controller.common.UserDetailHelper;
import com.langying.controller.service.IHzwReportService;

import com.langying.controller.service.IRandomCodeService;
import com.langying.controller.service.ISendMsgService;
import com.langying.controller.service.IUserService;
import com.langying.exception.ApiException;
import com.langying.handler.ResponseHandler;
import com.langying.models.UUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.UUID;

/**
 * Created by chenxu on 2016/3/21.
 */
@RestController
@Api("通用信息接口")
@RequestMapping("/common")
public class CommonAction {
    @Autowired
    ISendMsgService sendMsgService;

    @Autowired
    private IUserService userService;
    @Autowired
    private IHzwReportService shhzwService;

    @Autowired
    private IRandomCodeService randomCodeService;

    @ApiOperation(value = "发送手机动态码",notes = "发送所有手机短信动态码;type:1用户登录后验证,2:好作文查询报告验证,3:找回密码")
    @RequestMapping(value = "/sendMsg",method = RequestMethod.POST)
    @ResponseBody
    ResponseHandler sendMsg(HttpServletRequest request, @ApiParam("手机号") @RequestParam(value = "mobile",required = true)String mobile,
                            @ApiParam( value= "类型",allowableValues = "1,2,3")@RequestParam(name = "type",required = true)String type,
                            @ApiParam( value= "验证码")@RequestParam(name = "inputCode",required = true)String inputCode,
                            @ApiParam( value= "uuid")@RequestParam(name = "uuid",required = true)String uuid) throws Exception {
        String token="";
        if(CommonConstant.MsgType.LoginUpdate.getValue().equals(type)){//登录后验证用户信息
            try {
                token = UserDetailHelper.getUserDetail().getToken();
            }catch (Exception e){
                throw new ApiException("0401");
            }
        }
        RequestVerify.verify(request);
        boolean flag=randomCodeService.checkRandomCode(request,inputCode);//校验图片验证码
        if(!flag){
            throw new ApiException("9012");
        }
        Map map=sendMsgService.getMsg(token,mobile,type);
        if(map!=null&&map.containsKey("time")){//从缓存中获取手机号和动态码
            long millis=(System.currentTimeMillis()-((Long)map.get("time")))/1000;
            if(millis<60)
                throw new ApiException("2001","请等待"+(60-millis)+"秒后,再发送动态码");
        }
        if(CommonConstant.MsgType.LoginUpdate.getValue().equals(type)){//完善手机号
            UUser user=new UUser();
            user.setUserId(UserDetailHelper.getUserDetail().getUserId());
            user.setUserPhone(mobile);
            if(userService.checkMobileRepeat(user)!=0) {//验证手机好是否已被使用，未使用
                throw new ApiException("2013");
            }
        }
        else if(CommonConstant.MsgType.FindPassword.getValue().equals(type)){//找回密码
            if(!userService.findCountByParams(CommonConstant.getResMap("userPhone",mobile)).equals(1))
                throw new ApiException("9013");
        }
        sendMsgService.sendMsg(token,mobile,type);
        return ResponseHandler.getResponse();
    }

    @ApiOperation(value="验证手机动态码",notes = "发送所有手机短信动态码;<br>type:1用户登录后验证,2:好作文查询报告验证，3找回密码")
    @RequestMapping(value = "/verMsg",method = RequestMethod.POST)
    @ResponseBody
    ResponseHandler verMsg( @ApiParam("手机号") @RequestParam(name = "mobile",required = true)String mobile,
                            @ApiParam(value="类型",allowableValues = "1,2,3")@RequestParam(name = "type",required = true)String type,
                            @ApiParam("动态码") @RequestParam(name = "code",required = true)String code,
                            @ApiParam("姓名") @RequestParam(name = "name",required = false)String name) throws Exception {
        String token="";
        if(CommonConstant.MsgType.LoginUpdate.getValue().equals(type)){//登录后验证用户信息
            try {
                token = UserDetailHelper.getUserDetail().getToken();
            }catch (Exception e){
                throw new ApiException("0401","访问失效,请重新登录");
            }
        } else if(CommonConstant.MsgType.SSHZW.getValue().equals(type)){
            if(name==null||name.length()==0){
                throw new ApiException("2001","请输入用户名");
            }
        }
        Map map=sendMsgService.getMsg(token,mobile,type);
        if(map!=null&&map.size()!=0){
            if(map.containsKey("code")&&code.equals(map.get("code").toString())) {
                if(CommonConstant.MsgType.LoginUpdate.getValue().equals(type)){
                    UUser user=new UUser();
                    user.setUserId(UserDetailHelper.getUserDetail().getUserId());
                    user.setUserPhone(mobile);
                    userService.modifyBasic(user);
                    sendMsgService.clearMsg(token, mobile, type);
                    return ResponseHandler.getResponse();
                }else if(CommonConstant.MsgType.SSHZW.getValue().equals(type)){
                    Map essayMap=shhzwService.essayList(UUID.randomUUID().toString(),mobile,name);
                    sendMsgService.clearMsg(token,mobile,type);
                    essayMap.remove("user");
                    return ResponseHandler.getResponse(essayMap);
                }else if(CommonConstant.MsgType.FindPassword.getValue().equals(type)){
                    String uuid=UUID.randomUUID().toString();
                    sendMsgService.userInfoUuidByMobile(uuid,mobile);
                    return ResponseHandler.getResponse(CommonConstant.getResMap("uuid",uuid));
                }else {
                    sendMsgService.clearMsg(token,mobile,type);
                    return ResponseHandler.getResponse();
                }

            }
            throw new ApiException("2007");
        }else{
            throw new ApiException("2010");
        }

    }


    @ApiOperation("获取uuid")
    @RequestMapping(value = "/uuid",method = RequestMethod.GET)
    String token(HttpSession session){
        return session.getId();
    }

    @ApiOperation("获取验证码")
    @RequestMapping(value = "getSecurityCode",method = RequestMethod.GET)
    void securityCode(@ApiParam("uuid")@RequestParam(value = "uuid",required = false) String uuid,HttpServletRequest request, HttpServletResponse response){

        randomCodeService.createRandomCode(request,response);
    }
}
