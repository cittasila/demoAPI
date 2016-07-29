package com.langying.controller.service;

import com.langying.common.contant.CommonConstant;
import com.langying.common.models.Order;
import com.langying.controller.common.UserDetailHelper;
import com.langying.controller.mapper.RUserDoquestionMapper;
import com.langying.controller.mapper.RUserQuestionOptionMapper;
import com.langying.controller.resourcemapper.TQuestionOptionMapper;
import com.langying.models.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by chenxu on 2016/4/19.
 */
@Service
public class DoExercisesService extends  BaseService<RUserDoquestion> implements IDoExercisesService{

    private RUserDoquestionMapper userDoquestionMapper;
    @Autowired
    void setMapper(RUserDoquestionMapper mapper) {
        this.userDoquestionMapper = mapper;
        super.baseMapper=userDoquestionMapper;
    }
    @Autowired
    private RUserQuestionOptionMapper userQuestionOptionMapper;
    @Autowired
    private IQuestionInfoService questionInfoService;
    @Autowired
    private IUserBookService userBookService;
    @Autowired
    private IQuestionOptionService questionOptionService;
    @Autowired
    private TQuestionOptionMapper questionOptionMapper;



    /**
     * 现在只有“阅读理解”和“原文阅读”是开放的， 2016 年 5 月 11 日
     * @param bookId
     * @return
     * @throws Exception
     */
    @Override
    public BookStatusInfo exercisesStatus(String bookId) throws Exception {
        int userId= UserDetailHelper.getUserDetail().getUserId();
        RUserBook userBook=(RUserBook) userBookService.findObj(CommonConstant.getResMap("isActive","1","bookId",bookId,"userId",userId));

        // 查询学生这本书是否已经有做题记录，有做题记录就永远解锁了

        // SELECT * FROM `r_user_doquestion`
        // WHERE `user_id` = '686100' AND book_id = 'e2c0ea37-3a5f-424e-8f5d-e702fe37a366'

        Integer doQuestionRescord = userDoquestionMapper.selectCountByParams(CommonConstant.getResMap("bookId",bookId,"userId",userId));

        // 当 read_status = 0（正在阅读） 或者 2（未开始阅读） 的时候返回 false
        boolean readStatus=!("0".equals(userBook.getReadStatus())||"2".equals(userBook.getReadStatus()));
        BookStatusInfo bookStatusInfo=new BookStatusInfo();
        bookStatusInfo.setBookId(bookId);
        // 0:未开通,1:未解锁,2:已解锁
        bookStatusInfo.setCloze("0"); // 完形填空
        bookStatusInfo.setLexicalGrammar("0"); // 词汇语法
        bookStatusInfo.setListeningTraining("0"); // 听力训练
        bookStatusInfo.setReadingGuide("0"); // 阅读引导
        bookStatusInfo.setReadTheText("0"); // 原文跟读
        bookStatusInfo.setTextReading("2"); // 原文阅读

        // questionstyleids 1 和 2 是常量，表示单选，可以查看 t_question_style 这张表的内容
        // count 表示题目的数量
        int count=questionInfoService.findCountByParams(CommonConstant.getResMap("status","1","articleid",bookId,"questionstyleids","'1','2'"));
        if(count==0) {//阅读理解的题目不存在
            // 阅读理解
            bookStatusInfo.setReadingComprehension("0");
        }else {//原文阅读已读完为2已解锁，否则为1;
            // 0：正在阅读，1：阅读完成 , 2: 未开始阅读 3:练习完成
            // 如果阅读理解有题目，并且 read_status = 0 （正在阅读） 或者 2 （未开始阅读），则显示 未解锁
            // 如果阅读理解有题目，并且 read_status = 1 （阅读完成） 或者 3 （练习完成），则显示 已经解锁
            bookStatusInfo.setReadingComprehension((readStatus || doQuestionRescord>0) ? "2" : "1");
            // 如果 ReadingComprehension 已经解锁，就去查询学生做题的记录，并且判断对错
            if("2".equals(bookStatusInfo.getReadingComprehension())){
                // 查询学生已经做的题目的数量，如果等于上面的 count，就计算分数（直接显示分数）

                // 找 r_user_doquestion 表中  do_status = 1 和 is_active = 1 的数量
                // 查询用户已经做完的题目
                Integer userFinishedCount = this.findCountByParams(CommonConstant.getResMap("doStatus","1","isActive","1","bookId",bookId,"userId",userId));
                // 如果学生已经做的题目的数量 小于上面的 count，就显示未完成（返回 -1）
                if(userFinishedCount < count){
                    bookStatusInfo.setReadingComprehensionScore("-1");
                }else{
                    // 计算用户做对的题目的数量
                    Integer userFinishedRightCount = this.queryUserFinishedRightCount(CommonConstant.getResMap("bookId",bookId,"userId",userId));
                    // 计算正确率
                    bookStatusInfo.setReadingComprehensionScore(calculateScore(userFinishedRightCount,count));
                }
            }
        }
        return bookStatusInfo;
    }

    private String calculateScore(Integer rightNum,Integer allNum){
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        return numberFormat.format((float)rightNum/(float)allNum * 100);
    }


    /**
     * 读取该书籍题型下的所有题目
     * @param bookId
     * @param questionType
     * @return
     * @throws Exception
     */
    @Cacheable(value="question",key = "'question'+#bookId+'_'+#questionType")
    @Override
    public Map questionList(String bookId,int questionType) throws Exception {
        List<TQuestionInfo> list=questionInfoService.findListByParams(CommonConstant.getResMap("articleid",bookId,"status","1","activity",questionType), Order.asc("id"));
        Map map=new HashMap();
        for(int i=0;i<list.size();i++){
            map.put(i,list);
        }
        return map;
    }
    @Override
    @Transactional
    public Map<String, Object>  getNewExercises(String bookId) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
        //查询当前做题轮次
        param.put("bookId", bookId);//bookid
        param.put("isActive","1");//当前正在做题记录
        param.put("userId",UserDetailHelper.getUserDetail().getUserId());
        List<RUserDoquestion> userDoquestionsL=userDoquestionMapper.selectListByParams(param,null,null,null);
        Integer doTimes=0;
        if(userDoquestionsL.size()>0){
            doTimes=userDoquestionsL.get(0).getUserDotimes();
        }
        //修改历史做题
        RUserDoquestion userDoquestionP=new RUserDoquestion();
        userDoquestionP.setIsActive("0");
        userDoquestionP.setUserId(UserDetailHelper.getUserDetail().getUserId());
        userDoquestionP.setBookId(bookId);
        userDoquestionMapper.updateUserDoQuestionActive(userDoquestionP);

        //创建新的做题信息
        param=null;
        param = new HashMap<String, Object>();
        param.put("status","1");
        param.put("articleid",bookId);//bookid
        param.put("questionstyleids","'1','2'");//选择题

        List<TQuestionInfo> questionInfoL=questionInfoService.findListByParams(param,Order.asc("makedate"));
        param=null;
        param = new HashMap<String, Object>();
        param.put("status","1");
        param.put("articleid",bookId);
        List<TQuestionOption> questionOptionl=questionOptionMapper.selectListWithQuestionInfoByParams(param,"a.questionoption asc");

        RUserDoquestion userDoquestion;
        List<ExercisesInfo> exercisesInfoL=new ArrayList<ExercisesInfo>();
        ExercisesInfo exercisesInfo;
        List<ExercisesOptionInfo> exercisesOptionInfoL;
        ExercisesOptionInfo exercisesOptionInfo;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String time = sdf.format(date);
        int sort=0;
        for(TQuestionInfo questionInfoObj:questionInfoL){
            userDoquestion=new RUserDoquestion();
            sort++;
            userDoquestion.setIsActive("1");
            userDoquestion.setBookId(bookId);
            userDoquestion.setUserId(UserDetailHelper.getUserDetail().getUserId());
            userDoquestion.setCreateTime(time);
            userDoquestion.setDoStatus("0");
            userDoquestion.setQuestionId(questionInfoObj.getId());
            userDoquestion.setSort(sort);
            userDoquestion.setUpdateTime(date);
            userDoquestion.setUserDotimes(doTimes+1);
            userDoquestionMapper.insertSelective(userDoquestion);

            exercisesInfo=new ExercisesInfo();
            exercisesInfo.setUserDoquestionId(userDoquestion.getUserDoquestionId());
            exercisesInfo.setSort(userDoquestion.getSort());
            exercisesInfo.setQuestion(questionInfoObj.getQuestion());
            exercisesInfo.setDoStatus(userDoquestion.getDoStatus());
            exercisesInfo.setQuestionId(userDoquestion.getQuestionId());
            exercisesOptionInfoL=new ArrayList<ExercisesOptionInfo>();
            for(TQuestionOption questionOptionObj:questionOptionl){
                if(questionInfoObj.getId().equals(questionOptionObj.getQuestionid())){
                    exercisesOptionInfo=new ExercisesOptionInfo();
                    exercisesOptionInfo.setQuestionId(questionOptionObj.getQuestionid());
                    exercisesOptionInfo.setIsRight(questionOptionObj.getAnswer());
                    exercisesOptionInfo.setQuestionOption(questionOptionObj.getQuestionoption());
                    exercisesOptionInfo.setQuestionOptionId(questionOptionObj.getId());
                    exercisesOptionInfoL.add(exercisesOptionInfo);
                }
            }
            exercisesInfo.setExercisesOptionInfoL(exercisesOptionInfoL);
            exercisesInfoL.add(exercisesInfo);
        }
        result.put("exercisesList",exercisesInfoL);
        result.put("exercisesCount",sort);
        return result;
    }
    @Override
      public Map<String, Object>  getCurrentExercises(String bookId) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
        //查询当前做题轮次
        param.put("bookId", bookId);//bookid
        param.put("isActive","1");//当前正在做题记录
        param.put("userId",UserDetailHelper.getUserDetail().getUserId());
        List<RUserDoquestion> userDoquestionsL=userDoquestionMapper.selectListByParams(param,null,null,"sort asc");



//        param=null;
//        param = new HashMap<String, Object>();
//        param.put("status","1");
//        param.put("articleid",bookId);//bookid
//        param.put("questionstyleids","'1','2'");//选择题
//        List<TQuestionInfo> questionInfoL=questionInfoService.findListByParams(param,Order.asc("makedate"));
//        RUserDoquestion userDoquestion;
        List<ExercisesInfo> exercisesInfoL=new ArrayList<ExercisesInfo>();
        ExercisesInfo exercisesInfo;

        param=null;
        param = new HashMap<String, Object>();
        param.put("status","1");
        param.put("articleid",bookId);
        List<TQuestionOption> questionOptionl=questionOptionMapper.selectListWithQuestionInfoByParams(param,"a.questionoption asc");
        List<ExercisesOptionInfo> exercisesOptionInfoL;
        ExercisesOptionInfo exercisesOptionInfo;

        int sort=0;
        for(RUserDoquestion userDoquestion1Obj:userDoquestionsL){
            sort++;
            exercisesInfo=new ExercisesInfo();
            exercisesInfo.setUserDoquestionId(userDoquestion1Obj.getUserDoquestionId());
            exercisesInfo.setSort(userDoquestion1Obj.getSort());
            exercisesInfo.setDoStatus(userDoquestion1Obj.getDoStatus());
            exercisesInfo.setQuestionId(userDoquestion1Obj.getQuestionId());
            exercisesOptionInfoL=new ArrayList<ExercisesOptionInfo>();
            for(TQuestionOption questionOptionObj:questionOptionl){
                if(userDoquestion1Obj.getQuestionId().equals(questionOptionObj.getQuestionid())){
                    exercisesInfo.setQuestion(questionOptionObj.getQuestion());
                    exercisesOptionInfo=new ExercisesOptionInfo();
                    exercisesOptionInfo.setQuestionId(questionOptionObj.getQuestionid());
                    exercisesOptionInfo.setIsRight(questionOptionObj.getAnswer());
                    exercisesOptionInfo.setQuestionOption(questionOptionObj.getQuestionoption());
                    exercisesOptionInfo.setQuestionOptionId(questionOptionObj.getId());
                    exercisesOptionInfoL.add(exercisesOptionInfo);

                }
            }
            exercisesInfo.setExercisesOptionInfoL(exercisesOptionInfoL);
            exercisesInfoL.add(exercisesInfo);
        }
        result.put("exercisesList",exercisesInfoL);
        result.put("exercisesCount",sort);
        return result;
    }
    @Override
    public Map<String, Object>  getExercisesOptionById(String exercisesId) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("status","1");
        param.put("questionid",exercisesId);//问题id
        List<TQuestionOption> questionoptionL=questionOptionService.findListByParams(param,Order.asc("questionoption"));
        //TQuestionInfo questionInfo=questionInfoService.findObjByKey(exercisesId);
        List<ExercisesOptionInfo> exercisesOptionInfoL=new ArrayList<ExercisesOptionInfo>();
        ExercisesOptionInfo exercisesOptionInfo;
       for(TQuestionOption questionOptionObj:questionoptionL){
           exercisesOptionInfo=new ExercisesOptionInfo();
           exercisesOptionInfo.setQuestionId(questionOptionObj.getQuestionid());
           exercisesOptionInfo.setIsRight(questionOptionObj.getAnswer());
           exercisesOptionInfo.setQuestionOption(questionOptionObj.getQuestionoption());
           exercisesOptionInfo.setQuestionOptionId(questionOptionObj.getId());

           exercisesOptionInfoL.add(exercisesOptionInfo);
        }
        result.put("exercisesOptionList",exercisesOptionInfoL);
        return result;
    }
    @Override
    public Map<String, Object>  getFinishExercisesById(Integer userDoquestionId) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
        RUserDoquestion userDoquestion=userDoquestionMapper.selectByPrimaryKey(userDoquestionId);
        TQuestionInfo questionInfo= questionInfoService.findObjByKey(userDoquestion.getQuestionId());
        param.put("status","1");
        param.put("questionid",questionInfo.getId());//问题id
        List<TQuestionOption> questionoptionL=questionOptionService.findListByParams(param,Order.asc("questionoption"));
        param=null;
        param = new HashMap<String, Object>();
        param.put("userDoquestionId",userDoquestionId);
        List<RUserQuestionOption> userQuestionOptionL=userQuestionOptionMapper.selectListByParams(param,null,null,null);
        if(userQuestionOptionL.size()>0){

        }
        ExercisesInfo exercisesInfo=new ExercisesInfo();
        exercisesInfo.setUserDoquestionId(userDoquestionId);
        exercisesInfo.setSort(userDoquestion.getSort());
        exercisesInfo.setDoStatus(userDoquestion.getDoStatus());
        exercisesInfo.setQuestionId(userDoquestion.getQuestionId());
        exercisesInfo.setQuestion(questionInfo.getQuestion());
        exercisesInfo.setUserIsRight(userQuestionOptionL.get(0).getIsright());
        exercisesInfo.setUserSelectedOptionId(userQuestionOptionL.get(0).getUserOptionId());

        List<ExercisesOptionInfo> exercisesOptionInfoL=new ArrayList<ExercisesOptionInfo>();
        ExercisesOptionInfo exercisesOptionInfo;
        for(TQuestionOption questionOptionObj:questionoptionL){
            exercisesOptionInfo=new ExercisesOptionInfo();
            exercisesOptionInfo.setQuestionId(questionOptionObj.getQuestionid());
            exercisesOptionInfo.setIsRight(questionOptionObj.getAnswer());
            exercisesOptionInfo.setQuestionOption(questionOptionObj.getQuestionoption());
            exercisesOptionInfo.setQuestionOptionId(questionOptionObj.getId());

            exercisesOptionInfoL.add(exercisesOptionInfo);
        }
        exercisesInfo.setExercisesOptionInfoL(exercisesOptionInfoL);
        result.put("exercisesInfo",exercisesInfo);
        return result;
    }
    @Override
    public Map<String, Object>  saveExercisesAnswer(Integer userDoquestionId,String userOptionId) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
        RUserDoquestion userDoquestion=userDoquestionMapper.selectByPrimaryKey(userDoquestionId);
        userDoquestion.setUpdateTime(new Date());
        userDoquestion.setUserId(UserDetailHelper.getUserDetail().getUserId());
        userDoquestion.setDoStatus("1");//做完
        userDoquestionMapper.updateByPrimaryKeySelective(userDoquestion);

        //TQuestionInfo questionInfo= questionInfoService.findObjByKey(userDoquestion.getQuestionId());
        param.put("status","1");
        param.put("answer","1");//正确答案
        param.put("questionid",userDoquestion.getQuestionId());//问题id
        List<TQuestionOption> questionoptionL=questionOptionService.findListByParams(param,null);
        String isRight="0";
        if(questionoptionL.size()>0){
            if(userOptionId.equals(questionoptionL.get(0).getId())){
                isRight="1";
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String time = sdf.format(date);
        RUserQuestionOption userQuestionOption=new RUserQuestionOption();
        userQuestionOption.setCreateTime(time);
        userQuestionOption.setUpdateTime(date);
        userQuestionOption.setUserId(UserDetailHelper.getUserDetail().getUserId());
        userQuestionOption.setUserDoquestionId(userDoquestionId);
        userQuestionOption.setUserOptionId(userOptionId);
        userQuestionOption.setIsright(isRight);
        userQuestionOption.setIsActive("1");
        userQuestionOptionMapper.insertSelective(userQuestionOption);
        return result;
    }
    @Override
    public Map<String, Object>  getDoExercisesResult(String bookId) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("bookId", bookId);//bookid
        param.put("isActive","1");//当前正在做题记录
        param.put("userId",UserDetailHelper.getUserDetail().getUserId());
        List<RUserDoquestion> userDoquestionsL=userDoquestionMapper.selectListWithOptionInfoByParams(param,"a.sort asc");
        int total=userDoquestionsL.size();//总题数
        int rightTotal=0;
        Date beginDate=new Date();//开始时间
        Date endDate=new Date();//结束时间
        List<ExercisesInfo> exercisesInfoL=new ArrayList<ExercisesInfo>();
        ExercisesInfo exercisesInfo;
        for(RUserDoquestion userDoquestionObj:userDoquestionsL){
            exercisesInfo=new ExercisesInfo();
            exercisesInfo.setUserDoquestionId(userDoquestionObj.getUserDoquestionId());
            exercisesInfo.setSort(userDoquestionObj.getSort());
            exercisesInfo.setUserIsRight(userDoquestionObj.getIsright());
            if("1".equals(userDoquestionObj.getIsright())){
                rightTotal++;
            }
            if(userDoquestionObj.getSort()==1){
                beginDate= StringUtils.isBlank(userDoquestionObj.getCreateTime()) ? null : new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss").parse( userDoquestionObj.getCreateTime());
            }
            if(userDoquestionObj.getSort()==total){
                endDate=userDoquestionObj.getUpdateTime();
            }
            exercisesInfoL.add(exercisesInfo);
        }
        long l=endDate.getTime()-beginDate.getTime();
//        long day=l/(24*60*60*1000);
//        long hour=(l/(60*60*1000)-day*24);
//        long min=((l/(60*1000))-day*24*60-hour*60);
//        long s=(l/1000-day*24*60*60-hour*60*60-min*60);
//        String usedTime=""+day+"天"+hour+"小时"+min+"分"+s+"秒";//耗时
//        String avgTime=(l/(60*1000))/total+"分"+((l/(60*1000))%total)*0.1*60+"秒";
        long usedTime=l/1000;//耗时
        float avgTime=(float)(l/1000)/total;
        float rightPercent=(float)rightTotal/total;//正确率
        result.put("total",total);
        result.put("usedTime",usedTime);
        result.put("avgTime",avgTime);
        result.put("rightPercent",rightPercent);
        result.put("exercisesInfoL",exercisesInfoL);
        return result;
    }

    @Override
    public Integer queryUserFinishedRightCount(Map<String, Object> params) {
        return userQuestionOptionMapper.queryUserFinishedRightCount(params);
    }

    @Override
    public <K> K addBasic(RUserDoquestion obj) throws Exception {
        return null;
    }

    @Override
    public void modifyBasic(RUserDoquestion obj) throws Exception {

    }

    @Override
    public void delBasic(RUserDoquestion obj) throws Exception {

    }
}
