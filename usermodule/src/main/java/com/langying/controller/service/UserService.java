package com.langying.controller.service;

import com.langying.common.contant.CommonConstant;
import com.langying.controller.mapper.UUserMapper;
import com.langying.models.UUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by chenxu on 2016/3/12.
 */
@Service
public class UserService extends BaseService<UUser> implements IUserService {


    UUserMapper uUserMapper;

    @Autowired
    void setuUserMapper(UUserMapper uUserMapper) {
        this.uUserMapper = uUserMapper;
        super.baseMapper=uUserMapper;
    }


    @Override
    public UUser queryAllUserInfo(String userName) {
        return uUserMapper.queryByUserName(userName);
    }

    @Override
    public List<UUser> queryTeacherBySchoolId(String schoolId) {
        Map map= CommonConstant.getResMap("roleId",42,"schoolId",schoolId);
        return uUserMapper.queryUserListByParam(map);
    }

    @Override
    public List<UUser> queryStudentByTeacherId(String teacherId) {
        Map map= CommonConstant.getResMap("roleId",38,"teacherId",teacherId);
        return uUserMapper.queryUserListByParam(map);
    }

    @Override
    public Integer addBasic(UUser obj) throws Exception {
        uUserMapper.insert(obj);
        return obj.getUserId();
    }

    @Override
    public void modifyBasic(UUser obj) throws Exception {
        uUserMapper.updateByPrimaryKeySelective(obj);
    }

    @Override
    public void delBasic(UUser obj) throws Exception {

    }

    @Override
    public int checkMobileRepeat(UUser obj) throws Exception{
        Map map= CommonConstant.getResMap("userPhone",obj.getUserPhone(),"notUserId",obj.getUserId());
        return uUserMapper.selectCountByParams(map);
    }

    /***
     * 查询班级中的学生列表
     * @param classesId
     * @return
     */
    @Override
    public List<UUser> queryClassesUserListByClassesId(String classesId) {
        Map map= CommonConstant.getResMap("classesId",classesId,"roleId","38");
        return uUserMapper.queryClassesUserListByParam(map);
    }
}
