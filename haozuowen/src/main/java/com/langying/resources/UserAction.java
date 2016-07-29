package com.langying.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.langying.common.contant.CommonConstant;
import com.langying.controller.Verification.RequestVerify;
import com.langying.controller.common.UserDetailHelper;
import com.langying.controller.security.SimpleAuthenticationManager;
import com.langying.controller.service.*;
import com.langying.exception.ApiException;
import com.langying.handler.PasswordHelper;
import com.langying.handler.ResponseHandler;
import com.langying.models.URegisterGrade;
import com.langying.models.UUser;
import com.langying.models.UUserProductHistory;
import com.langying.toolbox.utils.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenxu on 2016/3/8.
 */
@RestController
@Api("用户相关信息信息")
@RequestMapping("/user")
public class UserAction {

    private static Logger logger = LoggerFactory.getLogger(UserAction.class);

    @Value("${reading.user.gold.initNum}")
    private Integer initNum;

    @Value("${reading.registerUser.gold.initNum}")
    private Integer registerUserInitNum;

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserProductHistoryService uUserProductHistoryService;
    @Autowired
    private ISendMsgService sendMsgService;
    @Autowired
    private IGoldService goldService;
    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private ITokenService tokenService;

    @Autowired
    private IURegisterGradeService uRegisterGradeService;

    @Autowired
    protected SimpleAuthenticationManager authenticationManager;


    @ApiOperation(value = "用户信息", response = UUser.class)
    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    ResponseHandler userInfo(HttpSession session, @RequestHeader(name = "authorization", required = false) String authorization) {
        logger.debug("initNum => " + initNum);
        logger.debug("initNum => " + registerUserInitNum);
        UUser user = UserDetailHelper.getUserDetail();
        user = userService.queryAllUserInfo(user.getUserName());

        if("8".equals(user.getUserRegisterType())){
            // 根据 user_id 查询 grade_id
            try {
                URegisterGrade uRegisterGrade = (URegisterGrade) uRegisterGradeService.findObj(CommonConstant.getResMap("userId",user.getUserId()));
                if(uRegisterGrade!=null){
                    String gradeId = uRegisterGrade.getGradeId();
                    user.setGradeId(gradeId);
                    String gradeName = getGradeNameById(gradeId);
                    user.setGradeName(gradeName);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 如果用户注册类型不是 7 ，就不让登录
        user.setToken(session.getId());
        // 查询是否有金币账户，如果有了金币账号，就不做操作
        String userRegisterType = user.getUserRegisterType();
        if (queryIfInitGold(user)) {
            if (CommonConstant.UserRegisterType.SDHAOZUOWEN.getValue().equals(userRegisterType) && user.getAmount() == null) {
                user.setAmount(initNum);
            }
            if (CommonConstant.UserRegisterType.INTER.getValue().equals(userRegisterType) && user.getAmount() == null) {
                user.setAmount(registerUserInitNum);
            }
        }
        if (user.getAmount() == null) {
            user.setAmount(0);
        }
        tokenService.tokenUser(user.getToken(), user);
        return ResponseHandler.getResponse(user);
    }


    private String getGradeNameById(String gradeId){
        String gradeName = null;
        switch (gradeId){
            case "GM":
                gradeName = "GMAT";
                break;
            case "GR":
                gradeName = "GRE";
                break;

            case "SA":
                gradeName = "SAT";
                break;
            case "TO":
                gradeName = "TOEFL";
                break;
            case "IE":
                gradeName = "IELTS";
                break;
            case "K1":
                gradeName = "幼儿园";
                break;
            case "PS":
                gradeName = "预备年级";
                break;
            case "H1":
                gradeName = "高一";
                break;
            case "H2":
                gradeName = "高二";
                break;
            case "H3":
                gradeName = "高三";
                break;
            case "M1":
                gradeName = "初一";
                break;
            case "M2":
                gradeName = "初二";
                break;
            case "M3":
                gradeName = "初三";
                break;
            case "P1":
                gradeName = "一年级";
                break;
            case "P2":
                gradeName = "二年级";
                break;
            case "P3":
                gradeName = "三年级";
                break;
            case "P4":
                gradeName = "四年级";
                break;
            case "P5":
                gradeName = "五年级";
                break;
            case "P6":
                gradeName = "六年级（预初）";
                break;
        }
        return gradeName;
    }

    /**
     * @param type 产品选择页中应该填写 “product”
     * @param data 根据不同的 type，填写不同格式的 data
     *             <p>
     *             <p>
     *             如果 type = "product"，data 的格式应该为 {"product_id":"1","info":"谷歌浏览器，安卓手机"}
     */
    @ApiOperation("记录用户行为")
    @RequestMapping(value = "/log", method = RequestMethod.GET)
    @ResponseBody
    public ResponseHandler userLog(HttpServletRequest request,
                                   @RequestParam String type,
                                   @RequestParam String data) throws ApiException {
        // 获得登录用户的基本信息
        UUser user = UserDetailHelper.getUserDetail();
        Integer userId = user.getUserId();
        logger.debug("userId => " + userId);
        logger.debug("type => " + type);
        logger.debug("data => " + data);
        /**
         * 类型是产品选择页，解析 product_id 和 info 部分
         */
        if ("product".equals(type)) {
            Map<String, String> map = null;
            try {
                ObjectMapper mapper = new ObjectMapper();
                map = mapper.readValue(data, Map.class);
            } catch (IOException e) {
                throw new ApiException("3001");
            }
            String productId = map.get("product_id");
            logger.debug("productId => " + productId);
            String userAgent = request.getHeader("user-agent");
            UUserProductHistory uUserProductHistory = new UUserProductHistory();
            uUserProductHistory.setUserId(userId + "");
            uUserProductHistory.setProductId(productId);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            uUserProductHistory.setLoginDate(DateUtil.formatTime(timestamp, "yyyy-MM-dd HH:mm:ss"));
            uUserProductHistory.setUserAgent(userAgent);
            uUserProductHistoryService.insertUUserProductHistory(uUserProductHistory);
        }
        return ResponseHandler.getResponse();
    }

    @ApiOperation("通过手机验证后修改密码")
    @RequestMapping(value = "/changePswByKey", method = RequestMethod.POST)
    @ResponseBody
    public ResponseHandler changePswByKey(HttpServletRequest request,
                                          @ApiParam("验证后的key") @RequestParam(value = "key", required = true) String key,
                                          @ApiParam("新密码") @RequestParam(value = "password", required = true) String password)
            throws Exception {
        UUser user = sendMsgService.userInfoUuidByMobile(key, null);
        sendMsgService.clearUserInfoUuid(key);

        if (user.getUserId() == null) {
            throw new ApiException("2014");
        } else {
            UUser userUpdate = new UUser();
            userUpdate.setUserId(user.getUserId());
            userUpdate.setUserPwd(PasswordHelper.getPassword(password));
            userService.modifyBasic(userUpdate);
        }
        return ResponseHandler.getResponse();

    }

    @ApiOperation("通过原密码修改密码")
    @RequestMapping(value = "/changePswByOldPsw", method = RequestMethod.POST)
    @ResponseBody
    public ResponseHandler changePswByOldPsw(HttpServletRequest request,
                                             @ApiParam("原密码") @RequestParam(value = "oldPassword", required = true) String oldPassword,
                                             @ApiParam("新密码") @RequestParam(value = "newPassword", required = true) String newPassword)
            throws Exception {
        RequestVerify.verify(request);
        UUser user = userService.findObjByKey(UserDetailHelper.getUserDetail().getUserId());
        if (!user.getUserPwd().equals(PasswordHelper.getPassword(oldPassword))) {
            throw new ApiException("2015");
        } else {
            UUser userUpdate = new UUser();
            userUpdate.setUserId(user.getUserId());
            userUpdate.setUserPwd(PasswordHelper.getPassword(newPassword));
            userService.modifyBasic(userUpdate);
        }
        return ResponseHandler.getResponse();

    }

    @ApiOperation(value = "修改用户信息", notes = "修改用户信息userGender(性别) 1:男,0:女")
    @RequestMapping(value = "/modifyUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseHandler modifyUserInfo(HttpServletRequest request, @ApiParam("姓名") @RequestParam(value = "userRealName", required = false) String userRealName,
                                          @ApiParam(value = "性别", allowableValues = "1,0") @RequestParam(value = "userGender", required = false) String userGender,
                                          @ApiParam("电子邮件") @RequestParam(value = "userEmail", required = false) String userEmail,
                                          @ApiParam("家长姓名") @RequestParam(value = "userParentName", required = false) String userParentName,
                                          @ApiParam("家长手机号") @RequestParam(value = "userParentPhone", required = false) String userParentPhone)
            throws Exception {
        RequestVerify.verify(request);
        Integer userid = UserDetailHelper.getUserDetail().getUserId();
        UUser user = new UUser();
        user.setUserId(userid);
        if (userRealName == null || userRealName.trim().length() == 0) {
            throw new ApiException("9017");
        }
        user.setUserRealName(userRealName);

        if (userGender != null) {
            user.setUserGender(userGender);
        }
        if (userEmail != null) {
            user.setUserEmail(userEmail);
        }
        if (userParentName != null) {
            user.setUserParentName(userParentName);
        }
        if (userParentPhone != null) {
            user.setUserParentPhone(userParentPhone);
        }
        userService.modifyBasic(user);
        return ResponseHandler.getResponse();

    }


    public Boolean queryIfInitGold(UUser user) {
        Boolean flag = false;
        String userRegisterType = user.getUserRegisterType();
        if (CommonConstant.UserRegisterType.SDHAOZUOWEN.getValue().equals(userRegisterType) && user.getAmount() == null) {
            logger.debug("山东好作文用户登录，但并未创建金币账号。");
            flag = goldService.initGoldAndDetail(user.getUserId(), initNum);
        }
        if (CommonConstant.UserRegisterType.INTER.getValue().equals(userRegisterType) && user.getAmount() == null) {
            logger.debug("山东好作文用户登录，但并未创建金币账号。");
            flag = goldService.initGoldAndDetail(user.getUserId(), registerUserInitNum);
        }
        return flag;
    }

    @ApiOperation(value = "检查唯一性", notes = "检查唯一性")
    @RequestMapping(value = "/checkUniq", method = RequestMethod.GET)
    @ResponseBody
    public boolean checkUniq(@ApiParam("登录名") @RequestParam(value = "loginName", required = false) String loginName,
                             @ApiParam("电子邮件") @RequestParam(value = "userEmail", required = false) String userEmail) {
        Map map = new HashMap();
        int num = 0;
        if (loginName != null && loginName.length() > 0) {
            map.put("userName", loginName);
            num = userService.findCountByParams(map);
            map.remove("userName");
        }
        if (userEmail != null && userEmail.length() > 0) {
            map.put("userEmail", userEmail);
            if (num == 0)
                num = userService.findCountByParams(map);
        }
        return num == 0;
    }

    /**
     * 注册接口
     *
     * @param userName
     * @param userEmail
     * @param password
     * @return
     * @throws ApiException
     */
    @ApiOperation(value = "注册", notes = "注册", response = UUser.class)
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseHandler register(HttpSession session, HttpServletRequest request, @ApiParam("登录名") @RequestParam(value = "userName", required = true) String userName,
                                    @ApiParam("电子邮件") @RequestParam(value = "userEmail", required = true) String userEmail,
                                    @ApiParam("密码") @RequestParam(value = "password", required = true) String password) throws Exception {
        Map map = new HashMap();
        RequestVerify.verify(request);
        if (userName != null && userName.length() > 0) {
            map.put("userName", userName);
            if (userService.findCountByParams(map) != 0) {
                throw new ApiException("9022");
            }
            map.remove("userName");
        }
        if (userEmail != null && userEmail.length() > 0) {
            map.put("userEmail", userEmail);
            if (userService.findCountByParams(map) != 0) {
                throw new ApiException("9023");
            }
        }
        UUser user = new UUser();
        user.setUserName(userName);
        user.setUserEmail(userEmail);
        user.setUserPwd(PasswordHelper.getPassword(password));
        user.setAmount(0);
        user.setUserEffective("1");
        user.setUserRegisterType(CommonConstant.UserRegisterType.INTER.getValue());
        // user.setClassesName("");
        user.setUserGender("1");
        // user.setSchoolName("");
        user.setUserLoginDate(new Date());
        user.setLoginTimes(1);
        user.setRoleId("38");
        // 自由注册默认为教学用户
        user.setUserType("1");
        user.setToken(session.getId());

        userService.addBasic(user);
        // userRoleService.addBasic(new UUserRoleKey(38,user.getUserId()));
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, user.getUserPwd());
        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager
                .authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        UUser user1 = UserDetailHelper.getUserDetail();
        return ResponseHandler.getResponse(user1);
    }


    /**
     * 自由注册用户保存年级（为自由注册用户绑定年级）
     *
     *
     * @return
     */
    @ApiOperation(value = "自由注册用户设置年级信息")
    @RequestMapping(value = "/registerUserSaveGrade", method = RequestMethod.GET)
    public ResponseHandler registerUserSaveGrade(
            @ApiParam(required = true,value = "年级信息")
            @RequestParam(required = true, value = "grade_id") String gradeId) throws Exception {
        logger.debug("grade_id " + gradeId);
        // 获得登录用户的基本信息
        UUser user = UserDetailHelper.getUserDetail();
        Integer userId = user.getUserId();
        String userRegisterType = user.getUserRegisterType();

        logger.debug("userId => " + userId);
        logger.debug("userRegisterType => " + userRegisterType);

        if (!"8".equals(userRegisterType)) {
            // 该类型用户没有权限访问
            throw new ApiException("9032");
        }
        URegisterGrade uRegisterGrade = (URegisterGrade) uRegisterGradeService.findObj(CommonConstant.getResMap("userId",userId));
        if(uRegisterGrade==null ){
            // 如果该用户的年级字段值为 ""，才执行插入数据操作
            uRegisterGrade = new URegisterGrade();
            uRegisterGrade.setUserId(userId);
            uRegisterGrade.setGradeId(gradeId);
            Integer insertNum = uRegisterGradeService.addBasic(uRegisterGrade);
            System.out.println(insertNum);
        }else{
            throw new ApiException("9034");
        }
        return ResponseHandler.getResponse();
    }

}

