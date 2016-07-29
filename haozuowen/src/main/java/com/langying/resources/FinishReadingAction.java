package com.langying.resources;

import com.langying.common.contant.CommonConstant;
import com.langying.controller.common.UserDetailHelper;
import com.langying.controller.service.*;
import com.langying.handler.ResponseHandler;
import com.langying.models.RReadingLog;
import com.langying.models.RUserBook;
import com.langying.models.UUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yucy on 2016/3/16.
 */
@RestController
@Api("完成阅读")
@RequestMapping("/finishReading")
public class FinishReadingAction {

    @Autowired
    IArticleEvaluateLabelService articleEvaluateLabelService;

    @Autowired
    IReadingLogService readingLogService;

    @Autowired
    IUserBookService userBookService;

    @Autowired
    IArticleInfoService articleInfoService;

    @Autowired
    private IQuestionInfoService questionInfoService;

    @ApiOperation("提交评论")
    @RequestMapping(value = "/commitComment",method = RequestMethod.POST)
    @ResponseBody
    ResponseHandler commitComment(
            @ApiParam("书籍ID")@RequestParam(name = "bookId",required = true)String bookId,
            @ApiParam("评价ID")@RequestParam(name = "evaluateId",required = true) String evaluateId){
        Map<String,Object> map=new HashMap<String,Object>();
        try{
            int userId= UserDetailHelper.getUserDetail().getUserId();
            Date date=new Date();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time=sdf.format(date);
            RReadingLog readingLog=new RReadingLog();
            readingLog.setBookId(bookId);
            readingLog.setUserId(userId);
            readingLog.setCreateTime(time);
            readingLog.setUpdateTime(date);
            readingLog.setEvaluateId(evaluateId);
            readingLog.setIsActive("1");
            readingLog.setIsFinish("1");
            readingLogService.addBasic(readingLog);



            // 提交书本评价的时候，如果这本书没有习题，就将用户这本书的状态设置为“3”（练习完成）。
            // 先查询这本书的状态，如果是已经完成就什么都不做
            RUserBook rUserBook = (RUserBook)userBookService.findObj(CommonConstant.getResMap("userId",userId,"bookId",bookId));
            String readStatus = rUserBook.getReadStatus();
            // 如果状态是练习完成，就什么都不做
            if("3".equals(readStatus)){

            }else if("1".equals(readStatus)){
                // 如果是阅读完成了，就查询这本书下面有没有习题，如果没有习题就将状态设置为完成
                Integer bookExeCount = questionInfoService.findCountByParams(CommonConstant.getResMap("articleid",bookId,"status","1"));
                if(bookExeCount==0){
                    // 更新这本书的状态为 练习完成
                    rUserBook.setReadStatus("3");
                    userBookService.modifyBasic(rUserBook);
                }
            }



            return ResponseHandler.getResponseCon("0000","操作成功");
        }catch (Exception e){
//            Log.
            return ResponseHandler.getResponseCon("0001","提交评价失败");
        }
    }

    @ApiOperation("获取评价列表")
    @RequestMapping(value = "/getCommentList",method = RequestMethod.GET)
    @ResponseBody
    ResponseHandler getCommentList(){
        Map<String,Object> map=new HashMap<String,Object>();
        try {
            map = CommonConstant.getListMap(articleEvaluateLabelService.getCommentLabelList());
            return ResponseHandler.getResponse(map);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseHandler.getResponseCon("0001","评价列表获取失败");
        }
    }

    @ApiOperation("完成阅读更改状态")
    @RequestMapping(value = "/finishReading",method = RequestMethod.POST)
    @ResponseBody
    ResponseHandler finishReading(@ApiParam("书籍ID")@RequestParam(name = "bookId",required = true)String bookId){
        Map<String,Object> param=new HashMap<String,Object>();
        Map<String,Object> map=new HashMap<String,Object>();
        try {
            int userId= UserDetailHelper.getUserDetail().getUserId();
            param.put("isActive","1");
            param.put("bookId",bookId);
            param.put("userId",userId);
            List<RUserBook> userBookList=userBookService.findListByParams(param,null);
            if(userBookList==null||userBookList.size()==0){
                return ResponseHandler.getResponseCon("0002","该用户尚未购买该书");
            }else{
                RUserBook userBook=userBookList.get(0);
                userBook.setReadStatus("1");
                userBookService.modifyBasic(userBook);
                param=new HashMap<String, Object>();
                param.put("id",bookId);
//                map=articleInfoService.findObjByKey(bookId);
//                map=CommonConstant.getResMap(articleInfoService.findObjByKey(bookId));
                return ResponseHandler.getResponseCon("0000",articleInfoService.findObjByKey(bookId));
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseHandler.getResponseCon("0001","系统错误");
        }
    }
}
