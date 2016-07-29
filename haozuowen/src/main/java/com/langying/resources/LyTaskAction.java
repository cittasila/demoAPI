package com.langying.resources;

import com.fasterxml.jackson.annotation.JsonView;
import com.langying.common.contant.CommonConstant;
import com.langying.controller.service.*;
import com.langying.exception.ApiException;
import com.langying.handler.PasswordHelper;
import com.langying.handler.ResponseHandler;
import com.langying.models.UClasses;
import com.langying.models.UUser;
import com.langying.models.UUserProductHistory;
import com.langying.toolbox.utils.MD5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by chenxu on 2016/4/26.
 */
@RestController
@Api("朗鹰作业")
@RequestMapping("/lyTask")
public class LyTaskAction {

    @Autowired
    IUserService userService;
    @Autowired
    IClassesService classesService;
    @Autowired
    ITokenService tokenService;

    @Autowired
    IUserProductHistoryService userProductHistoryService;

    @ApiOperation(value = "教师登录", notes = "教师登录", response = UUser.class)
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    @JsonView(UUser.ILyTaskUserView.class)
    public ResponseHandler teacherLogin(@ApiParam("登录名") @RequestParam(value = "loginName", required = true) String loginName,
                                        @ApiParam("密码") @RequestParam(value = "password", required = true) String password,
                                        @ApiParam("提交时间") @RequestParam(value = "postTime", required = true) String postTime, @ApiParam("签名") @RequestParam(value = "pkey", required = true) String pkey, HttpSession session) throws Exception {
        if (!this.verification(postTime,pkey,loginName+password,"tokjbhw65n46qptxci")){
            throw new ApiException("9030");
        }
        UUser user = userService.queryAllUserInfo(loginName);
        if (user == null || !PasswordHelper.getPassword(password).equals(user.getUserPwd())) {
            throw new ApiException("9007");
        }
        if (!"42".equals(user.getRoleId())) {
            throw new ApiException("9031");
        }
       /* if (!(user.getUserRegisterType() == null||user.getUserRegisterType().length()==0 || CommonConstant.UserRegisterType.SDHAOZUOWEN.getValue().equals(user.getUserRegisterType()) || CommonConstant.UserRegisterType.SYSTEMIMPORT.getValue().equals(user.getUserRegisterType()))) {
            throw new ApiException("9032");
        }*/
        String token = session.getId();
        user.setToken(token);
        tokenService.tokenUser(token, user);
        UUserProductHistory productHistory = new UUserProductHistory();
        productHistory.setProductId("7");//朗鹰
        productHistory.setUserId(user.getUserId().toString());
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(date);
        productHistory.setLoginDate(time);
        userProductHistoryService.insertUUserProductHistory(productHistory);
        return ResponseHandler.getResponse(user);
    }

    /**
     * 验证密钥
     *
     * @param ptime
     * @param pkey
     * @param data
     * @param miyao
     * @return
     */
    private boolean verification(String ptime, String pkey, String data, String miyao) {
        long currentTimeStamp = System.currentTimeMillis();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());
        String checkString = date + ptime + data + miyao;
        if (!MD5Util.getMD5Str(checkString).equals(pkey)) {
            return false;
        }
        return true;
    }

    @ApiOperation(value = "班级列表", notes = "班级列表")
    @RequestMapping(path = "/classes", method = RequestMethod.GET)
    @JsonView(UClasses.ClassesView.class)
    public ResponseHandler classes(@ApiParam("token") @RequestHeader(value = "token", required = true) String token) throws Exception {
        Map map = tokenService.tokenUser(token);
        if (map == null) {
            throw new ApiException("0401");
        }
        return ResponseHandler.getResponse(classesService.selectClassesStudent((Integer) map.get("userId")));
    }
    @ApiOperation(value = "班级列表", notes = "班级列表")
    @RequestMapping(path = "/students", method = RequestMethod.GET)
    @JsonView(UUser.ClassesUserView.class)
    public ResponseHandler students(@ApiParam("token") @RequestHeader(value = "token", required = true) String token,@ApiParam("classesId") @RequestParam(value = "classesId", required = true) Integer classesId) throws Exception {

        Map map = tokenService.tokenUser(token);
        if (map == null) {
            throw new ApiException("0401");
        }
        return ResponseHandler.getResponse(CommonConstant.getResMap("list",userService.queryClassesUserListByClassesId(classesId+"")));
    }
}
