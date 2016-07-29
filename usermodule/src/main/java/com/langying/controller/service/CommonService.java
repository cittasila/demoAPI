package com.langying.controller.service;

import com.langying.common.contant.CommonConstant;
import com.langying.controller.mapper.UDistcdMapper;
import com.langying.controller.mapper.USchoolGradeMapper;
import com.langying.controller.mapper.USchoolMapper;
import com.langying.controller.mapper.UUserMapper;
import com.langying.models.UDistcd;
import com.langying.models.USchool;
import com.langying.models.UUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 区域服务
 * Created by chenxu on 2016/3/15.
 */
@Service
public class CommonService implements ICommonService {

    @Autowired
    UDistcdMapper uDistcdMapper;
    @Autowired
    USchoolMapper schoolMapper;

    @Autowired
    UUserMapper uUserMapper;


    /**
     * 获取城市列表
     * @param code
     * @return
     */
    public List<Map> getArea(String code){
        Map map=new HashMap();
        if(code==null||""==code){
            map.put("levCd","1");
        }else{
            map.put("pDistCd",code);
        }
        List<UDistcd> list=uDistcdMapper.selectListByParams(map,null,null,null);
        List<Map> areaList=new ArrayList<Map>();
        for(UDistcd distcd:list){
            String name="";
            if(code==null||""==code){
                name=distcd.getProvName();
            }else if(code.endsWith("0000")){
                name=distcd.getCityName();
            }else {
                name=distcd.getCntyName();
            }
            Map mapArea=CommonConstant.getResMap("code",distcd.getDistCd(),"name",name);
            areaList.add(mapArea);
        }
        return areaList;
    }

    @Override
    public List<Map> getSchool(String code) {
        Map mapArea= CommonConstant.getResMap("distCd",code);
        List<Map> schoolList=new ArrayList<Map>();
        List<USchool> list= schoolMapper.selectListByParams(mapArea,null,null,null);
        for(USchool school:list){
            Map mapSchool=CommonConstant.getResMap("id",school.getSchoolId(),"name",school.getSchoolName());
            schoolList.add(mapSchool);
        }
        return schoolList;
    }
}
