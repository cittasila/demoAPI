package com.langying.controller.service;

import com.langying.controller.mapper.UClassesMapper;
import com.langying.models.UClasses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenxu on 2016/3/12.
 */
@Service
public class ClassesService extends BaseService<UClasses>  implements IClassesService{

    UClassesMapper classesMapper;



    @Autowired
    void setClassesMapper(UClassesMapper classesMapper) {
        this.classesMapper = classesMapper;
        super.baseMapper=classesMapper;
    }


    @Override
    public <K> K addBasic(UClasses obj) throws Exception {
        return null;
    }

    @Override
    public void modifyBasic(UClasses obj) throws Exception {

    }

    @Override
    public void delBasic(UClasses obj) throws Exception {

    }

    /***
     * 查询教师所教班级列表及班级的人数
     * @param teacherId
     * @return
     */
    @Override
    public List<UClasses> selectClassesStudent(Integer teacherId) {
        Map map =new HashMap();
        map.put("teacherId",teacherId);
        List<UClasses> classes= classesMapper.selectClassesStudent(map);
        List<UClasses> list=new ArrayList<UClasses>();
        for(UClasses u:classes){
            u.setTeacherId(teacherId);
            list.add(u);
        }
        return list;
    }
}
