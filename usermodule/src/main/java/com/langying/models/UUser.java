package com.langying.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.langying.handler.ResponseHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
@ApiModel("用户信息")
public class UUser implements Serializable {
    public interface   AmountUserView{}


    public  interface   ClassesUserView extends ResponseHandler.ResponseWrapperView {}
    public  interface   ILyTaskUserView extends ClassesUserView {}
    @ApiModelProperty("用户编号")
    @JsonView(ClassesUserView.class)
    private Integer userId;

    @ApiModelProperty("登录名")
    @JsonView(ClassesUserView.class)
    private String userName;

    @JsonIgnore
    private String userPwd;

    private Date userBuildDate;

    private Date userLoginDate;

    @ApiModelProperty("学号")
    @JsonView(ClassesUserView.class)
    private String userStudentId;

    @ApiModelProperty("用户姓名")
    @JsonView(ClassesUserView.class)
    private String userRealName;
    @ApiModelProperty(value = "性别",allowableValues="1男,0女")
    private String userGender;

    @ApiModelProperty(value = "生日")
    private Date userBirthday;

    @ApiModelProperty(value = "手机号")
    private String userPhone;

    @ApiModelProperty(value = "电子邮件")
    private String userEmail;

    @ApiModelProperty(value = "家长姓名")
    private String userParentName;

    @ApiModelProperty(value = "家长手机号")
    private String userParentPhone;

    @ApiModelProperty(value = "有效性")
    private String userEffective;

    @JsonIgnore
    private Integer tokenId;

    private Integer loginTimes;
    @JsonIgnore
    private Integer readingId;
    @JsonIgnore
    private Integer wordId;
    @JsonIgnore
    private Integer sjtuId;

    @ApiModelProperty(value = "用户注册类型",allowableValues = "8:inter自由注册,7:山东导入")
    private String userRegisterType;

    @ApiModelProperty(value = "学校名称")
    @JsonView(ILyTaskUserView.class)
    private String schoolName;

    @ApiModelProperty(value = "年级名称")
    @JsonView(ILyTaskUserView.class)
    private String gradeName;
    // @JsonIgnore
    private String gradeId;

    //@JsonView(ILyTaskUserView.class)
    private Integer classesId;
    private String userType;

    @ApiModelProperty(value = "班级名称")
    //@JsonView(ILyTaskUserView.class)
    private String classesName;
    @ApiModelProperty(value = "角色编号",allowableValues = "42:教师,38:学生")
    private String roleId;

    @JsonView(ILyTaskUserView.class)
    private String token;

    @JsonView(ILyTaskUserView.class)
    private String provName;

    @JsonView(AmountUserView.class)
    @ApiModelProperty(value = "金币数量")
    private Integer amount ;
    @JsonIgnore
    private Integer excelIndex;

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public Date getUserBuildDate() {
        return userBuildDate;
    }

    public void setUserBuildDate(Date userBuildDate) {
        this.userBuildDate = userBuildDate;
    }

    public Date getUserLoginDate() {
        return userLoginDate;
    }

    public void setUserLoginDate(Date userLoginDate) {
        this.userLoginDate = userLoginDate;
    }

    public String getUserStudentId() {
        return userStudentId;
    }

    public void setUserStudentId(String userStudentId) {
        this.userStudentId = userStudentId;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserParentName() {
        return userParentName;
    }

    public void setUserParentName(String userParentName) {
        this.userParentName = userParentName;
    }

    public String getUserParentPhone() {
        return userParentPhone;
    }

    public void setUserParentPhone(String userParentPhone) {
        this.userParentPhone = userParentPhone;
    }

    public String getUserEffective() {
        return userEffective;
    }

    public void setUserEffective(String userEffective) {
        this.userEffective = userEffective;
    }

    public Integer getTokenId() {
        return tokenId;
    }

    public void setTokenId(Integer tokenId) {
        this.tokenId = tokenId;
    }

    public Integer getLoginTimes() {
        return loginTimes;
    }

    public void setLoginTimes(Integer loginTimes) {
        this.loginTimes = loginTimes;
    }

    public Integer getReadingId() {
        return readingId;
    }

    public void setReadingId(Integer readingId) {
        this.readingId = readingId;
    }

    public Integer getWordId() {
        return wordId;
    }

    public void setWordId(Integer wordId) {
        this.wordId = wordId;
    }

    public Integer getSjtuId() {
        return sjtuId;
    }

    public void setSjtuId(Integer sjtuId) {
        this.sjtuId = sjtuId;
    }

    public String getUserRegisterType() {
        return userRegisterType;
    }

    public void setUserRegisterType(String userRegisterType) {
        this.userRegisterType = userRegisterType;
    }


    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getClassesName() {
        return classesName;
    }

    public void setClassesName(String classesName) {
        this.classesName = classesName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getClassesId() {
        return classesId;
    }

    public void setClassesId(Integer classesId) {
        this.classesId = classesId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getExcelIndex() {
        return excelIndex;
    }

    public void setExcelIndex(Integer excelIndex) {
        this.excelIndex = excelIndex;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getProvName() {
        return provName;
    }

    public void setProvName(String provName) {
        this.provName = provName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}