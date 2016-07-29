package com.langying.controller.service;


import com.langying.controller.mapper.HzwReportMapper;

import com.langying.controller.mapper.UUserMapper;
import com.langying.controller.mapper.UUserMobileHistoryMapper;
import com.langying.exception.ApiException;

import com.langying.models.*;
import com.langying.toolbox.utils.GetDataConfUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by chenxu on 2016/3/21.
 */
@Service
public class HzwReportService implements IHzwReportService{

    @Autowired
    HzwReportMapper mapper;

    @Autowired
    UUserMapper uUserMapper;

    @Autowired
    UUserMobileHistoryMapper userMobileHistoryMapper;

    @CachePut(cacheNames ="shhzw",key="'essayList'+#token")
    @Override
    public Map essayList(String token, String mobile, String userName) throws ApiException{
        UUser  user=uUserMapper.queryByUserName("lyο"+userName);
        if(user==null||user.getUserName().length()==0){
              user=uUserMapper.queryByUserName("yy"+userName);
            if(user==null||user.getUserName().length()==0) {
                user=uUserMapper.queryByUserName(userName);
                if(user==null||user.getUserName().length()==0) {
                    throw new ApiException("2008");
                }
            }
        }
        UUserMobileHistory userMobileHistory=new UUserMobileHistory();
        userMobileHistory.setUserId(user.getUserId());
        userMobileHistory.setCreateTime(new Date());
        userMobileHistory.setMobile(mobile);
        userMobileHistoryMapper.insert(userMobileHistory);
        String essayList="2678,2677,2676,2675,2743,2744,2745,2746,2674,2673,2672,2671";
        List list= mapper.essayList(user.getUserId(),essayList);
        Map map=new HashMap();
        map.put("list",list);
        map.put("user",user);
        map.put("token",token);
        return map;
    }

    @Cacheable(cacheNames ="shhzw",key="'essayList'+#token")
    @Override
    public Map getEssayList(String token){
        return new HashMap();
    }

    //@Cacheable(cacheNames ="shhzw",key="'essayList'+#token+':'+#assignmentId")
    @Override
    public Map report(Integer assignmentId,UUser user) {
        Map map=new HashMap();
        Map<String,Object> pointMap=new HashMap<String, Object>();
        Map<String,Object> classPointMap=new HashMap<String, Object>();
        Map<String,Object> gradePointMap=new HashMap<String, Object>();
        String assignmentIds="";
        pointMap=getPointMap(Integer.toString(assignmentId));
        for(Integer obj:getSameClassAssignment(assignmentId)){
            assignmentIds+=obj+",";
        }
        if(!"".equals(assignmentIds)){
            classPointMap=getPointMap(assignmentIds.substring(0,assignmentIds.length()-1));
        }
        assignmentIds="";
        for(Integer obj:getSameGradeAssignment(assignmentId)){
            assignmentIds+=obj+",";
        }
        if(!"".equals(assignmentIds)){
            gradePointMap=getPointMap(assignmentIds.substring(0,assignmentIds.length()-1));
        }
        map.put("detail",writingDetail(assignmentId,user));
        map.put("competencyAssessment",competencyAssessment(assignmentId,user,pointMap,classPointMap,gradePointMap));
        map.put("microSkillsAssessment",microSkillsAssessment(assignmentId,user,pointMap,classPointMap,gradePointMap));
        map.put("writingLevel",writingLevel(assignmentId,user.getClassesId()));
        map.put("errorList",error(assignmentId));
        map.put("reviews",reviews(assignmentId));
        return map;
    }

    private Map<String,Object> getPointMap(String assignmentIds){
        Map<String,Object> pointMap=new HashMap<String, Object>();
        List<Map<String,Object>> mapList=mapper.getAssignmentPoint(assignmentIds);
        if(mapList!=null&&mapList.size()>0){
            pointMap=mapList.get(mapList.size()-1);
        }
        return  pointMap;
    }

    /**
     * 写作详情
     * @param assignmentId
     * @param user
     * @return map
     */
     private Map writingDetail(Integer assignmentId,UUser user){
         List<Map<String,String>> essaySetTitleAndRequirement=mapper.getEssayTitleAndRequirement(assignmentId);
         Map<String,String> map=new HashMap<String, String>();
         List<Map<String,String>> contentInfoList=mapper.getAssignmentInfoByAssignmentId(assignmentId);
         List<Map<String,Object>> mapList=mapper.getSubmitCount(assignmentId);
         if(mapList!=null&&mapList.size()>0){
             map.put("subCount",mapList.get(mapList.size()-1).get("subCount").toString());
         }
         if(isEmpty(essaySetTitleAndRequirement)&&isEmpty(contentInfoList)) {
             Map<String,String> contentInfoMap=contentInfoList.get(0);
             Map<String,String> essaySetMap = essaySetTitleAndRequirement.get(0);
             map.put("studentName",user.getUserRealName());
             map.put("essay_set_title",essaySetMap.get("essay_set_title"));
             map.put("essay_set_requirement",essaySetMap.get("essay_set_requirement"));
             map.put("classes",user.getGradeName()+user.getClassesName());
             map.put("wordCount",contentInfoMap.get("assignment_history_word_count"));
             map.put("timeDiff",contentInfoMap.get("assignment_history_time"));
//             int ss=contentInfoMap.get("assignment_history_time");
             map.put("timeDiff",contentInfoMap.get("assignment_history_time"));
             String contentT=contentInfoMap.get("assignment_history_content");
             JSONObject jsonObj = new JSONObject().fromObject(contentT);
             TAssignmentInfo assignmentInfo=(TAssignmentInfo) JSONObject.toBean(jsonObj, TAssignmentInfo.class);
             map.put("assignmentContent",assignmentInfo.getAss_Body());
             List<Map<String,String>> teacherListMap=mapper.getTeacherNameByAssignmentId(assignmentId);
             if(teacherListMap!=null&&teacherListMap.size()==1){
                 map.put("teacher",teacherListMap.get(0).get("user_real_name"));
             }
             return map;
         }else {
             return null;
         }
    }

    /**
     * 作文能力数据评估
     * @param assignmentId
     * @param user
     * @return pointList
     */
    private List<Map> competencyAssessment(Integer assignmentId,UUser user,Map<String,Object> pointMap,Map<String,Object> classPointMap,Map<String,Object> gradePointMap){
        List<Map> pointList=new ArrayList<Map>();
        List<String> keyList=new ArrayList<String>();
        keyList.add("content");
        keyList.add("language");
        keyList.add("constuction");
        ScoreStandard scoreStandard=queryStandardById(assignmentId,user.getClassesId());
        pointList.add(putMapWithStandard(keyList,pointMap,"",scoreStandard));
        pointList.add(putMapWithStandard(keyList,classPointMap,"class-",scoreStandard));
        pointList.add(putMapWithStandard(keyList,gradePointMap,"grade-",scoreStandard));
        return pointList;
    }
    Map<String,Object> putMapWithStandard( List<String> keyList,Map<String,Object> pointMap,String prefix,ScoreStandard scoreStandard){
        Map<String,Object> map=new HashMap<String, Object>();
        double total=0;
        String standardscore="";
        if(pointMap!=null)
            for(String obj:keyList){
                if("content".equals(obj)){
                    standardscore=scoreStandard.getStandard_score1();
                }else if("language".equals(obj)){
                    standardscore=scoreStandard.getStandard_score2();
                }else if("constuction".equals(obj)){
                    standardscore=scoreStandard.getStandard_score3();
                }else{
                    standardscore="";
                }
                map.put(prefix+obj,pointMap.get(obj)+"/"+standardscore);
                total+=Double.parseDouble(pointMap.get(obj).toString());
                standardscore="";
            }
        map.put(prefix+"total",Double.toString(total));
        return map;
    }
    Map<String,Object> putMap( List<String> keyList,Map<String,Object> pointMap,String prefix){
        Map<String,Object> map=new HashMap<String, Object>();
        double total=0;
        if(pointMap!=null)
        for(String obj:keyList){
            map.put(prefix+obj,pointMap.get(obj));
            total+=Double.parseDouble(pointMap.get(obj).toString());
        }
        map.put(prefix+"total",Double.toString(total));
        return map;
    }


    /***
     * 微技能评估
     * @param assignmentId
     * @param user
     * @return pointList
     */
    private List<Map> microSkillsAssessment(Integer assignmentId,UUser user,Map<String,Object> pointMap,Map<String,Object> classPointMap,Map<String,Object> gradePointMap){
        List<Map> pointList=new ArrayList<Map>();
        List<String> keyList=new ArrayList<String>();
        keyList.add("org");
        keyList.add("dev");
        keyList.add("ss");
        keyList.add("wc");
        keyList.add("mech");
        pointList.add(putMap(keyList,pointMap,""));
        pointList.add(putMap(keyList,classPointMap,"class-"));
        pointList.add(putMap(keyList,gradePointMap,"grade-"));
        return pointList;
    }

    /***
     * 写作水平
     * @param assignmentId
     * @return
     */

    private Map writingLevel(Integer assignmentId,Integer classid){

        Map<String, Object> resultM = new HashMap<String, Object>();
        Float assignmentMarkSum =  mapper.getAssignmentMarkSumByAssignmentId(assignmentId);
        Float avgAssignmentMarkSum =  mapper.getAVGAssignmentMarkSumByAssignmentId(assignmentId,classid);
        List<ScoreStandard> scoreStandardL =  mapper.getStandardById(classid);
        ScoreStandard scoreStandard=new ScoreStandard();
        if(scoreStandardL.size()>0){
            String name = scoreStandardL.get(0).getGrade_name();
            String result =  scoreStandardL.get(0).getScore_standard_zh();
            if ("".equals(result)||result==null) {
                result = GetDataConfUtil.getPropertyValue("standard", name);
            }
            String[] ss = result.split(";");
            for (String s : ss) {
                String[] standard = s.split(",");
                if ("内容分".equals(standard[0])) {
                    scoreStandard.setStandard_name1("内容");
                    scoreStandard.setStandard_score1(standard[1]);
                }
                if ("语言分".equals(standard[0])) {
                    scoreStandard.setStandard_name2("语言");
                    scoreStandard.setStandard_score2(standard[1]);
                }
                if ("文采分".equals(standard[0])) {
                    scoreStandard.setStandard_name3("文采");
                    scoreStandard.setStandard_score3(standard[1]);
                }
                if ("结构分".equals(standard[0])) {
                    scoreStandard.setStandard_name3("结构");
                    scoreStandard.setStandard_score3(standard[1]);
                }
            }
        }
        Float Standard_score1=scoreStandard.getStandard_score1()==null||"".equals(scoreStandard.getStandard_score1())?0:Float.parseFloat(scoreStandard.getStandard_score1());
        Float Standard_score2=scoreStandard.getStandard_score2()==null||"".equals(scoreStandard.getStandard_score2())?0:Float.parseFloat(scoreStandard.getStandard_score2());
        Float Standard_score3=scoreStandard.getStandard_score3()==null||"".equals(scoreStandard.getStandard_score3())?0:Float.parseFloat(scoreStandard.getStandard_score3());
        Float mypoint = (assignmentMarkSum == null ? 0 : assignmentMarkSum)*(100/(Standard_score1+Standard_score2+Standard_score3));
        Float classpoint = (avgAssignmentMarkSum == null ? 0 : avgAssignmentMarkSum)*(100/(Standard_score1+Standard_score2+Standard_score3));


        resultM.put("mypoint", mypoint);
        resultM.put("classpoint", classpoint);

        return resultM;
    }
    public ScoreStandard queryStandardById(Integer assignmentId,Integer classid) {
        Map<String, Object> resultM = new HashMap<String, Object>();
        Float assignmentMarkSum =  mapper.getAssignmentMarkSumByAssignmentId(assignmentId);
        Float avgAssignmentMarkSum =  mapper.getAVGAssignmentMarkSumByAssignmentId(assignmentId,classid);
        List<ScoreStandard> scoreStandardL =  mapper.getStandardById(classid);
        ScoreStandard scoreStandard=new ScoreStandard();
        if(scoreStandardL.size()>0){
            String name = scoreStandardL.get(0).getGrade_name();
            String result =  scoreStandardL.get(0).getScore_standard_zh();
            if ("".equals(result)||result==null) {
                result = GetDataConfUtil.getPropertyValue("standard", name);
            }
            String[] ss = result.split(";");
            for (String s : ss) {
                String[] standard = s.split(",");
                if ("内容分".equals(standard[0])) {
                    scoreStandard.setStandard_name1("内容");
                    scoreStandard.setStandard_score1(standard[1]);
                }
                if ("语言分".equals(standard[0])) {
                    scoreStandard.setStandard_name2("语言");
                    scoreStandard.setStandard_score2(standard[1]);
                }
                if ("文采分".equals(standard[0])) {
                    scoreStandard.setStandard_name3("文采");
                    scoreStandard.setStandard_score3(standard[1]);
                }
                if ("结构分".equals(standard[0])) {
                    scoreStandard.setStandard_name3("结构");
                    scoreStandard.setStandard_score3(standard[1]);
                }
            }
        }
        return scoreStandard;
    }

    /**
     * 评语
     * @param assignmentId
     * @return
     */
    private Map reviews(Integer assignmentId){
        Map<String, Object> result = new HashMap<String, Object>();
        List<RcoreComment> rcoreCommentL =  mapper.getRcoreCommentByAssignmentId(assignmentId);
        if(rcoreCommentL.size()>0) {
            result.put("comment_ch", rcoreCommentL.get(0).getComment_ch());
            result.put("comment_en", rcoreCommentL.get(0).getComment_en());
        }else{
            result.put("comment_ch", null);
            result.put("comment_en", null);
        }
        return result;
    }

    /***
     * 纠错
     * @param assignmentId
     * @return
     */
    private List<Map> error(Integer assignmentId){
        return mapper.errorList(assignmentId);
    }

    private String changeToTime(String sec){
        String time="";
        try {
            long secLong = Long.parseLong(sec);
            long hour=secLong/3600;
            long minute=secLong%3600/60;
            long second=secLong%3600%60;
            time=hour>=0&&hour<10?"0"+Long.toString(hour):Long.toString(hour);
            time+=":";
            time=time+(minute>=0&&minute<10?"0"+Long.toString(minute):Long.toString(minute));
            time+=":";
            time=time+(second>=0&&second<10?"0"+Long.toString(second):Long.toString(second));
            return time;
        }catch (NumberFormatException e){
            return "00:00:00";
        }
    }

    private List<Integer> getSameClassAssignment(Integer assignmentId){
        List<Map<String,Integer>> assignmentClass= mapper.getAssignmentInfoBySameClass(assignmentId);
        List<Integer> classAssignmentIdList=new ArrayList<Integer>();
        for(Map<String,Integer> obj:assignmentClass){
            classAssignmentIdList.add(obj.get("assignment_id"));
        }
        return  classAssignmentIdList;
    }

    private List<Integer> getSameGradeAssignment(Integer assignmentId){
        List<Map<String,Integer>> assignmentGrade= mapper.getAssignmentInfoBySameGrade(assignmentId);
        List<Integer> gradeAssignmentIdList=new ArrayList<Integer>();
        for(Map<String,Integer> obj:assignmentGrade){
            gradeAssignmentIdList.add(obj.get("assignment_id"));
        }
        return gradeAssignmentIdList;
    }

    private Boolean isEmpty(List list){
        if(list!=null&&list.size()>0){
            return true;
        }else{
            return false;
        }
    }
}

