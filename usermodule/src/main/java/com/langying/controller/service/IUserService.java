package com.langying.controller.service;


import com.langying.models.UUser;

import java.util.List;

/**
 * Created by chenxu on 2016/3/12.
 */
public interface IUserService extends IBaseService<UUser>{
    public UUser queryAllUserInfo(String userName);

    /**
     * 通过学校编号查询教师列表
     * @param schoolId
     * @return
     */
    public List<UUser> queryTeacherBySchoolId(String schoolId);

    /**
     * 通过教师编号查询学生信息
     * @param teacherId
     * @return
     */
    public List<UUser> queryStudentByTeacherId(String teacherId);

    /**
     * 查询绑定的手机是否重复
     * * @param obj
     * @return
     * @throws Exception
     */
    public int checkMobileRepeat(UUser obj) throws Exception;

    public List<UUser> queryClassesUserListByClassesId(String classesId);
}
