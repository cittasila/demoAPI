package com.langying.controller.service;

import com.langying.common.contant.CommonConstant;
import com.langying.controller.mapper.*;
import com.langying.exception.ApiException;
import com.langying.handler.PasswordHelper;
import com.langying.models.*;

import org.jruby.ir.operands.Hash;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.*;

/**
 * Created by chenxu on 2016/3/16.
 */

/**
 * 用户管理服务
 */
@Service
public class UserManageService implements IUserManageService {

    private final ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(this.getClass());

    @Autowired
    UGradeMapper gradeMapper;
    @Autowired
    USchoolMapper schoolMapper;
    @Autowired
    USchoolGradeMapper schoolGradeMapper;
    @Autowired
    USemesterMapper semesterMapper;
    @Autowired
    UClassesMapper classesMapper;

    @Autowired
    UUserMapper uUserMapper;

    @Autowired
    UClassesUserMapper classesUserMapper;

    @Autowired
    UUserRoleMapper uUserRoleMapper;
    @Autowired
    UDistcdMapper uDistcdMapper;

    /**
     * 将年级转换为年份及学校类型编号
     *
     * @param gradeId
     * @return
     */
    private String dealGrade(String gradeId,String isGradeSixInPrimarySchool) throws ApiException {
        String first = gradeId.substring(0, 1);
        String second = gradeId.substring(1, 2);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int initial = 0;
        String schoolType = "";
//        if ("P".equals(first)) {
//            schoolType = "1";
//            initial = 0;
//        } else if ("M".equals(first)) {
//            schoolType = "2";
//            initial = 6;
//        } else if ("H".equals(first)) {
//            schoolType = "3";
//            initial = 9;
//        }
//        try {
//            int gradeYear = year - 2000 - Integer.parseInt(second);
//            int sum = Integer.parseInt(second) + initial;
//            if (sum >= 1 && sum <= 12)
//                return gradeYear + schoolType;
//        } catch (Exception e) {
//
//        }
        if("1".equals(isGradeSixInPrimarySchool)){//六年制
            if ("P".equals(first)) {
                schoolType = "1";
                initial = 0;
            } else if ("M".equals(first)) {
                schoolType = "2";
                initial = 6;
            } else if ("H".equals(first)) {
                schoolType = "3";
                initial = 9;
            }
        }else {//五年制
            if ("P".equals(first) && !"6".equals(second)) {
                schoolType = "1";
                initial = 0;
            } else if ("M".equals(first) || ("P".equals(first) &&"6".equals(second))) {
                schoolType = "2";
                initial = 6;
            } else if ("H".equals(first)) {
                schoolType = "3";
                initial = 9;
            }
        }
        try {
            int gradeYear;
            int secondint=0;
            if("1".equals(isGradeSixInPrimarySchool)) {//六年制
                secondint=Integer.parseInt(second);
                gradeYear = year - 2000 - secondint;
            }else{//五年制
                if("2".equals(schoolType)){//初中
                    if("6".equals(second)){
                        secondint=1;
                    }else{
                        secondint=Integer.parseInt(second)+1;
                    }
                    gradeYear = year - 2000 - secondint;
                }
                else{
                    secondint=Integer.parseInt(second);
                    gradeYear = year - 2000 - secondint;
                }
            }
            int sum = secondint + initial;
            if (sum >= 1 && sum <= 12)
                return gradeYear + schoolType;
        } catch (Exception e) {

        }
        throw new ApiException("2006");
    }

    /***
     * 1.先查询学校 判断学校是否存在，前缀码是否存在
     * 2.查询年级常量并将其放入gradeMap中
     * 3.循环excelResults
     * 3.1判断年级是否在年级gradeMap中，
     * 3.2判断年级是否在schoolGradeMap中(学校与年级的关系u_school_grade),如果不存在则创建关系然后放入schoolGradeMap
     * 3.3判断班级格式，判断班级是否在classesMap中如果不存在则创建该班级(u_classes),然后放入classesMap中
     * 3.4 判断教师学生角色
     * 3.4.1 如果是教师则判断班级和登录名是否在teacherMap中存在，如果存在则报错
     * 3.4.2 如果教师编号相同，姓名不同则报错
     * 否则将其加入teacherMap(HashSet),将登录名加入loginNameString中，然后创建user对象加入teacherList，
     * 3.4.2 如果是学生则判断登录名是否在userNameMap中存在，则报错
     * 否则将其加入userNameMap(HashSet),将登录名加入loginNameString中，然后创建user对象加入studentUserMap，
     * 4.通过loginNameString查询数据库中已存在的用户信息extUserList
     * 4.1学生：如果真实姓名和数据库相同则过滤(移除studentUserMap)，如果不同则报错；然后插入用户表及角色表,用户与班级关系表
     * 4.2教师：
     * 4.2.1如果数据库中登录名存在
     * 4.2.1.1登录真实姓名和数据库不相同则报错，否则忽略
     * 4.2.1.2插入班级与教师关系表
     * 4.2.2如果数据库中登录名不存在则插入教师用户表，角色表，用户与班级关系表
     *
     * @param excelResults
     * @param roleId
     * @param schoolId
     * @param oprUser
     * @throws ApiException
     */
    @Override
    public int dealUser(String[][] excelResults, String roleId, Integer schoolId, String oprUser, String alluserType) throws ApiException {
        USchool school = schoolMapper.selectByPrimaryKey(schoolId);
        if (school == null) {
            throw new ApiException("2003");
        }
        if (null == school.getPrefixCode() || school.getPrefixCode().length() < 1) {
            throw new ApiException("2004");
        }
        List<UUser> userList = new ArrayList<UUser>();
        Map<String, UUser> studentUserMap = new HashMap<String, UUser>();
        List<UGrade> gradeList = gradeMapper.selectListByParams(new HashMap<String, Object>(), null, null, null);
        Map<String, String> gradeMap = new HashMap();
        Map<String, USchoolGrade> schoolGradeMap = new HashMap();
        Map<String, UClasses> classesMap = new HashMap();
        List<UUser> teacherList = new ArrayList<UUser>();
        for (UGrade grade : gradeList) {
            gradeMap.put(grade.getGradeName(), grade.getGradeId());
        }
        String loginNameString = "";
        String ignoreMsg = "";
        HashMap<String, UUser> teacherMap = new HashMap();
        HashMap<String, UUser> teacherrealMap = new HashMap();
        Map<String, UUser> userNameMap = new HashMap<String, UUser>();
        USemester uSemester = semesterMapper.selectByParams(CommonConstant.getResMap("currentTime", true));
        if(excelResults.length>1&&excelResults[0].length>3){
            String userType=excelResults[0][2];
            if("42"==roleId&&!userType.startsWith("教师")){
                throw new ApiException("9009");
            }else if("38"==roleId&&!userType.startsWith("学生")){
                throw new ApiException("9010");
            }
        }else{
            throw new ApiException("9011");
        }
        for (int i = 1; i < excelResults.length; i++) {
            String grade = excelResults[i][0];
            String classesNo = excelResults[i][1];
            String realName = excelResults[i][2];

            String userNo = excelResults[i][3];
            if (grade == null || grade.trim().length() == 0 || classesNo == null || classesNo.trim().length() == 0 || realName == null || realName.trim().length() == 0 || userNo == null || userNo.trim().length() == 0) {
                String msg="";
                if(grade == null || grade.trim().length() == 0)
                    msg="年级";
                else if(classesNo == null || classesNo.trim().length() == 0)
                    msg="班级";
                else if(realName == null || realName.trim().length() == 0)
                    msg="姓名";
                else if(userNo == null || userNo.trim().length() == 0)
                    msg="编号";
                throw new ApiException("2001", "excel中第" + (i+1) + "行的"+msg+"不能为空,请检查");
            }
            userNo = userNo.trim();
            if(userNo.startsWith("‘")||userNo.startsWith("'"))
                userNo=userNo.substring(1);

            String gradeId = gradeMap.get(grade);
            if (gradeId == null)
                throw new ApiException("2001", "excel单元格A" + (i+1) + "中不存在该年级");
            if (!schoolGradeMap.containsKey(gradeId)) {//创建年级与学校的关系
                USchoolGrade uSchoolGrade = new USchoolGrade();
                uSchoolGrade.setGradeId(gradeId);
                uSchoolGrade.setSchoolId(schoolId);
                List<USchoolGrade> schoolGradeList = schoolGradeMapper.selectListByParams(CommonConstant.getResMap("gradeId", gradeId, "schoolId", schoolId), null, null, null);
                if (schoolGradeList == null || schoolGradeList.size() == 0) {
                    schoolGradeMapper.insert(uSchoolGrade);
                    schoolGradeMap.put(gradeId, uSchoolGrade);
                } else {
                    schoolGradeMap.put(gradeId, schoolGradeList.get(0));
                }
            }
            USchoolGrade uSchoolGrade = schoolGradeMap.get(gradeId);
            int classNo=0;
            try {
                 classNo = Integer.parseInt(excelResults[i][1]);
                if (classNo < 1 || classNo > 99)
                    throw new Exception("") {
                    };
            } catch (Exception e) {
                throw new ApiException("2001", "excel单元格B" + (i+1) + "中班级名称必须为1-99的数字");
            }
            String classes = classNo + "班";
            if (!classesMap.containsKey(gradeId + classes)) {
                List<UClasses> classesList = classesMapper.selectListByParams(CommonConstant.getResMap("classesName", classes, "schoolGradeId", uSchoolGrade.getSchoolGradeId()), null, null, null);
                if (classesList == null || classesList.size() == 0) {
                    UClasses uClasses = new UClasses();
                    uClasses.setSchoolGradeId(uSchoolGrade.getSchoolGradeId());
                    uClasses.setClassesBuildDate(new Date());
                    uClasses.setClassesName(classes);
                    uClasses.setSemesterId(uSemester.getSemesterId());
                    classesMapper.insert(uClasses);
                    classesMap.put(gradeId + classes, uClasses);
                } else {
                    classesMap.put(gradeId + classesList.get(0).getClassesName(), classesList.get(0));
                }
            }
            String prefixCode = school.getPrefixCode();
            String userName = "";
            Integer classId = classesMap.get(gradeId + classes).getClassesId();
            UUser user = new UUser();

            user.setUserRealName(realName);
            user.setUserBuildDate(new Date());
            user.setToken("1");
            user.setClassesName(classes);
            user.setUserEffective("1");
            user.setRoleId(roleId);
            user.setUserRegisterType("7");
            user.setClassesId(classId);
            user.setExcelIndex(i+1);
            if(alluserType!=null&&!"".equals(alluserType)) {
                user.setUserType(alluserType);
            }
            try{
                if(realName.getBytes("GBK").length>20){
                    throw new  ApiException("2012","第"+user.getExcelIndex()+"行,姓名不能超过20个字符");
                }}catch (Exception e){
                throw new  ApiException("2012",e.getMessage());
            }
            String gradeNo;
            try {
                String isGradeSixInPrimarySchool=uDistcdMapper.selectByPrimaryKey(school.getDistCd()).getIsGradeSixInPrimarySchool();

                gradeNo = dealGrade(gradeId,isGradeSixInPrimarySchool);
            } catch (ApiException e) {
                throw new ApiException("2006", "A" + user.getExcelIndex() + "的年级输入有误");
            }
            if ("42" == roleId) {//教师
                userName = prefixCode + userNo;
                int length=0;
                try {
                    length=userName.getBytes("GBK").length;
                    if ( length> 30) {
                        throw new ApiException("2012", "第" + user.getExcelIndex()+"行编号不能超过"+(30 - prefixCode.getBytes("GBK").length) + "个字符");
                    }
                }catch (Exception e){
                    throw new ApiException("2012", e.getMessage());
                }
                if (teacherrealMap.containsKey(userName)) {
                    UUser uUserTmp = teacherrealMap.get(userName);
                    if (!uUserTmp.getUserRealName().equals(realName))
                        throw new ApiException("2006", "第" + user.getExcelIndex() + "," + uUserTmp.getExcelIndex() + "行的教师的编号相同<br>但是真实姓名不同");
                }
                if (teacherMap.containsKey(classId + ":" + userName)) {//教师的班级和登录名不能重复
                    //throw new ApiException("2006","第"+user.getExcelIndex()+","+teacherMap.get(classId + ":" + userName).getExcelIndex()+"行,教师与班级的关系相同<br>请删除后操作");
                    ignoreMsg = ignoreMsg + "D" + user.getExcelIndex() + "(" + userName + "),";
                    continue;
                }
                teacherMap.put(classId + ":" + userName, user);
                teacherrealMap.put(userName, user);
                loginNameString = loginNameString + "'" + userName + "',";
                user.setUserName(userName);
                user.setUserPwd(PasswordHelper.getPassword(userName));
                teacherList.add(user);
            } else if ("38" == roleId) {//学生
                if(userNo!=null&&!"".equals(userNo)&&userNo.length()>1){
                    user.setUserStudentId(userNo.substring(userNo.length()-2,userNo.length()));
                }
                userName = prefixCode + gradeNo + String.format("%02d", Integer.parseInt(excelResults[i][1])) + userNo;
                int length=0;
                try {
                    length=userName.getBytes("GBK").length;
                    if ( length> 30) {
                        throw new ApiException("2012",  "第" + user.getExcelIndex()+"行编号不能超过"+ (30 - (prefixCode + gradeNo + String.format("%02d", Integer.parseInt(excelResults[i][1]))).getBytes("GBK").length) + "个字符");
                    }
                }catch (Exception e){
                    throw new ApiException("2012", e.getMessage());
                }
                if (userNameMap.containsKey(userName) && "38" == roleId) {//学生的学号不能重复
                     throw new ApiException("2006", "第" + userNameMap.get(userName).getExcelIndex()+","+user.getExcelIndex() + "行学生的编号,年级重复");
                    //ignoreMsg = ignoreMsg + "D" + user.getExcelIndex() + "(" + userName + "),";
                    //continue;
                }
                userNameMap.put(userName, user);
                loginNameString = loginNameString + "'" + userName + "',";
                user.setUserName(userName);
                user.setUserPwd(PasswordHelper.getPassword(userName));
                studentUserMap.put(user.getUserName(), user);
            }

        }
        int count=0;
        List<UUser> extUserList = new ArrayList<UUser>();
        if (loginNameString.length() > 0) {
            loginNameString = loginNameString.substring(0, loginNameString.length() - 1);
            extUserList = uUserMapper.selectListByParams(CommonConstant.getResMap("loginNameString", loginNameString), null, null, null);
        }
        if ("38" == roleId) {//学生的登录名去除数据库中重复的部分
            String extUserName = "";
            for (UUser extUser : extUserList) {
                UUser uUser = studentUserMap.get(extUser.getUserName());
                //  if (!uUser.getUserRealName().equals(extUser.getUserRealName()))
                //    throw new ApiException("2005", "第" + uUser.getExcelIndex() + "行" + "数据库中存在该用户编号<br>但真实姓名为:" + extUser.getUserRealName());
                studentUserMap.remove(extUser.getUserName());
                extUserName = extUserName + extUser.getUserName() + ",";
            }
            logger.info("excel中重复：" + ignoreMsg + "；数据库中重复：" + extUserName);
            for (String userName : studentUserMap.keySet()) {
                insertUsereAllInfo(studentUserMap.get(userName));
                ++count;
            }
        } else if ("42" == roleId) {
            Map<String, UUser> extTeacher = new HashMap();
            for (UUser extUser : extUserList) {
                extTeacher.put(extUser.getUserName(), extUser);
            }
            for (int i = 0; i < teacherList.size(); i++) {

                UUser uUser = teacherList.get(i);
                if (extTeacher.containsKey(uUser.getUserName())) {
                    UUser extUser = extTeacher.get(uUser.getUserName());
                    //if (!uUser.getUserRealName().equals(extUser.getUserRealName()))
                    //  throw new ApiException("2005", "第" + uUser.getExcelIndex() + "行" + "数据库中存在该用户编号<br>但真实姓名为:" + extUser.getUserRealName());
                    uUser.setUserId(extTeacher.get(uUser.getUserName()).getUserId());
                    // checkTeacherClasses(uUser);
                    if(insertUsereClasses(uUser)) ++count;

                } else {
                    Integer userId = insertUsereAllInfo(uUser);
                    uUser.setUserId(userId);
                    extTeacher.put(uUser.getUserName(), uUser);
                    ++count;
                }
            }
        }
        return count;
    }

    /**
     * 插入用户信息，班级关系
     *
     * @param uUser
     * @return
     */
    @Transactional
    public Integer insertUsereAllInfo(UUser uUser) throws ApiException {
        uUserMapper.insertSelective(uUser);

        UUserRoleKey uUserRoleKey = new UUserRoleKey();
        uUserRoleKey.setUserId(uUser.getUserId());
        uUserRoleKey.setRoleId(Integer.parseInt(uUser.getRoleId()));
        uUserRoleMapper.insert(uUserRoleKey);
        //if (uUser.getRoleId() == "42")
        //    checkTeacherClasses(uUser);
        UClassesUser classesUser = new UClassesUser();
        classesUser.setUserId(uUser.getUserId());
        classesUser.setUpdateDate(new Date());
        classesUser.setClassesId(uUser.getClassesId());
        classesUserMapper.insertSelective(classesUser);
        return uUser.getUserId();
    }

    /**
     * 插入教师班级关系
     *
     * @param uUser
     */
    private boolean insertUsereClasses(UUser uUser) {

        Integer userId = uUser.getUserId();
        UClassesUser classesUser = new UClassesUser();
        classesUser.setUserId(userId);
        classesUser.setUpdateDate(new Date());
        classesUser.setClassesId(uUser.getClassesId());
        try {
            classesUserMapper.insertSelective(classesUser);
        } catch (Exception e) {
            logger.info("已经存在关系" + uUser.getUserName() + ",classId:" + "" + uUser.getClassesId() + "className:" + uUser.getClassesName());
            return false;
        }
        return true;
    }

    /**
     * 检查该班级下是否已经有教师授课
     *
     * @param user
     * @throws ApiException
     */
    private void checkTeacherClasses(UUser user) throws ApiException {
        List<UUser> list = uUserMapper.queryClassesUserListByParam(CommonConstant.getResMap("roleId", "42", "classesId", user.getClassesId()));
        if (list == null || list.size() == 0) {

        } else if (list.size() > 1) {
            String userName = "";
            for (UUser uUser : list) {
                userName = userName + "," + uUser.getUserRealName();
            }
            throw new ApiException("2005", "第" + user.getExcelIndex() + "行,该班级" + user.getClassesName() + "(" + user.getClassesId() + ")已存在教师" + userName);
        } else {
            UUser extUser = list.get(0);
            if (!extUser.getUserId().equals(user.getUserId())) {
                throw new ApiException("2005", "第" + user.getExcelIndex() + "行,该班级已存在教师" + extUser.getUserRealName() + "(" + extUser.getUserName() + ")");
            }
        }
    }


}


