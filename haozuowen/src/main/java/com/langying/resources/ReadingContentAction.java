package com.langying.resources;

import com.langying.common.models.Order;
import com.langying.controller.common.UserDetailHelper;
import com.langying.controller.service.IArticleInfoService;
import com.langying.controller.service.IArticleSentenceService;
import com.langying.controller.service.IUserBookService;
import com.langying.controller.service.IUserOperateLogService;
import com.langying.exception.ApiException;
import com.langying.handler.ResponseHandler;
import com.langying.models.RUserBook;
import com.langying.models.RUserOperateLog;
import com.langying.models.TArticleInfo;
import com.langying.models.TArticleSentence;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by yucy on 2016/3/17.
 */

@RestController
@Api("阅读内容")
@RequestMapping("/readingContent")
public class ReadingContentAction {

    @Autowired
    IArticleInfoService articleInfoService;

    @Autowired
    IArticleSentenceService articleSentenceService;

    @Autowired
    IUserOperateLogService userOperateLogService;

    @Autowired
    IUserBookService userBookService;

    @ApiOperation("记录用户阅读行为数据")
    @RequestMapping(value = "/saveUserReadingLog",method = RequestMethod.GET)
    @ResponseBody
    ResponseHandler saveUserReadingLog(@ApiParam("书籍ID")@RequestParam(name = "bookId",required = true)String bookId,
            @ApiParam("行为序号(1.上一页  2.下一页  3.退出  4.点击音频)")@RequestParam(name = "operate",required = true)String operate){
        try {
            if("1".equals(operate)||"2".equals(operate)||"3".equals(operate)||"4".equals(operate)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                String time = sdf.format(date);
                int userId = UserDetailHelper.getUserDetail().getUserId();
                RUserOperateLog userOperateLog = new RUserOperateLog();
                userOperateLog.setBookId(bookId);
                userOperateLog.setUserId(userId);
                userOperateLog.setOperate(Integer.parseInt(operate));
                userOperateLog.setCreateTime(time);
                userOperateLogService.addBasic(userOperateLog);
                return ResponseHandler.getResponseCon("0000", "记录用户阅读行为成功");
            }else{
                return ResponseHandler.getResponseCon("0002", "operate传入异常( 1.上一页  2.下一页  3.退出  4.点击音频)，传入的为"+operate);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseHandler.getResponseCon("0001","记录用户阅读行为失败");
        }
    }

    @ApiOperation("开始阅读文章")
    @RequestMapping(value = "/startReading",method = RequestMethod.POST)
    @ResponseBody
    ResponseHandler startReading(@ApiParam("书籍ID")@RequestParam(name = "bookId",required = true)String bookId){
        Map<String,Object> map=new HashMap<String,Object>();
        try {
            int userId= UserDetailHelper.getUserDetail().getUserId();
            map.put("isActive","1");
            map.put("bookId",bookId);
            map.put("userId",userId);
            List<RUserBook> userBookList=userBookService.findListByParams(map,null);
            if(userBookList==null||userBookList.size()==0){
                return ResponseHandler.getResponseCon("0002","该用户尚未购买该书");
            }else{
                RUserBook userBook=userBookList.get(0);
                if("0".equals(userBook.getReadStatus())){
                    return ResponseHandler.getResponseCon("0003","该用户已经在阅读该书");
                }else if("1".equals(userBook.getReadStatus())){
                    return ResponseHandler.getResponseCon("0004","该用户已经完成该书阅读");
                }
                userBook.setReadStatus("0");
                userBookService.modifyBasic(userBook);
                return ResponseHandler.getResponseCon("0000","操作成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseHandler.getResponseCon("0001","系统错误");
        }
    }

    @ApiOperation("获取文章内容")
    @RequestMapping(value = "/getArticleInfo",method = RequestMethod.GET)
    @ResponseBody
    ResponseHandler getArticleInfo(@ApiParam("书籍ID")@RequestParam(name = "bookId",required = true)String bookId){
        try {
            int userId= UserDetailHelper.getUserDetail().getUserId();
            Map<String,Object> param=new HashMap<String,Object>();
            param.put("isActive","1");
            param.put("bookId",bookId);
            param.put("userId",userId);
            List<RUserBook> userBookList=userBookService.findListByParams(param,null);
            if(userBookList==null||userBookList.size()==0){
                throw  new ApiException("9016");
                //虚拟购买书籍
               /* SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                String time = sdf.format(date);
                RUserBook userBook=new RUserBook();
                userBook.setUserId(userId);
                userBook.setBookId(bookId);
                userBook.setCreateTime(time);
                userBook.setUpdateTime(date);
                userBook.setReadStatus("2");
                userBook.setIsActive("1");
                userBookService.addBasic(userBook);*/
//                return ResponseHandler.getResponseCon("0002","该用户尚未购买该书");
            }
//            else{
            // 更新用户阅读这本书的时间
            if(userBookList.size() == 1){
                RUserBook rUserBook = userBookList.get(0);
                rUserBook.setUpdateTime(new Date());
                userBookService.modifyBasic(rUserBook);
            }
            param=new HashMap<String,Object>();
            param.put("ids","'"+bookId+"'");
            param.put("status","2");
            List<TArticleInfo> articleInfoList=articleInfoService.findListByParams(param,null);
            if(articleInfoList!=null&&articleInfoList.size()==1){
                TArticleInfo articleInfo = articleInfoList.get(0);
                if(articleInfo.getImgurl()!=null&&!"".equals(articleInfo.getImgurl())){
                    articleInfo.setImgurl(articleInfo.getImgurl());
                }
                if(articleInfo.getPreviewcoachpath()!=null&&!"".equals(articleInfo.getPreviewcoachpath())){
                    articleInfo.setPreviewcoachpath(articleInfo.getPreviewcoachpath());
                }
                articleInfo.setContent(getSentenceListByBookIdGroupByPartAndSort(bookId));
                return ResponseHandler.getResponseCon("0000",articleInfo);
            }else{
                return ResponseHandler.getResponseCon("0003","未找到对应id文章或对应文章未发布");
            }
//            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseHandler.getResponseCon("0001","获取文章内容失败");
        }
    }

    private List<List<TArticleSentence>> getSentenceListByBookIdGroupByPartAndSort(String bookId){
        Map<String,Object> param=new HashMap<String,Object>();
        param.put("articleid",bookId);
        List<TArticleSentence> articleSentenceList=articleSentenceService.findListByParamsOrderByPartAndSortAsc(param);
        int part=0;
        List<List<TArticleSentence>> partList=new ArrayList<List<TArticleSentence>>();
        List<TArticleSentence> sortList=new ArrayList<TArticleSentence>();
        for(TArticleSentence obj:articleSentenceList){
            if(obj.getPic()!=null&&!"".equals(obj.getPic())){
                obj.setPic(obj.getPic());
                //obj.setPicwidthandheight("1080*780");
            }
            if(obj.getRecordingpath()!=null&&!"".equals(obj.getRecordingpath())){
                obj.setRecordingpath(obj.getRecordingpath());
            }
            if(part!=obj.getPart()){
                part=obj.getPart();
                partList.add(sortList);
                sortList=new ArrayList<TArticleSentence>();
            }
            sortList.add(obj);
        }
        partList.add(sortList);
        return  partList;
    }
}
