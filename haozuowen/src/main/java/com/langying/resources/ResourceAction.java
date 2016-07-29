package com.langying.resources;

import com.langying.common.models.Order;
import com.langying.common.models.RollPage;
import com.langying.controller.common.UserDetailHelper;
import com.langying.controller.service.IArticleInfoService;
import com.langying.controller.service.IGradeBookService;
import com.langying.controller.service.IResourceService;
import com.langying.controller.service.ITopicInfoService;
import com.langying.handler.ResponseHandler;
import com.langying.models.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by thtanghao on 2016/3/16.
 */
@RestController
@Api("作文资源管理")
@RequestMapping("/resource")
public class ResourceAction {


    private static Logger logger = LoggerFactory.getLogger(ResourceAction.class);

    @Autowired
    private ITopicInfoService topicInfoService;
    @Autowired
    private IArticleInfoService articleInfoService;
    @Autowired
    private IResourceService resourceService;

    @Autowired
    private IGradeBookService gradeBookService;

    @ApiOperation(value = "查询所有年级",notes = "gradeLabel 年级 id；gradeName 年级名称",response = ResponseHandler.class)
    @RequestMapping(value = "queryAllGrade",method = RequestMethod.GET)
    @ResponseBody
    public ResponseHandler queryAllGrade(){
        return ResponseHandler.getResponse(gradeBookService.queryAllGrade());
    }


    /**
     * 查询全部文章主题（进入阅读页面以后左边列表显示）
     * @return
     * @throws Exception
     */
    @ApiOperation("查询全部文章主题")
    @RequestMapping(value = "getTopicInfoList",method = RequestMethod.GET)
    @ResponseBody
    public ResponseHandler getTopicInfoList() throws Exception{
        Map<String, Object> param = new HashMap<>();
        // 状态,1:启用，0:禁用(删除)
        param.put("topic_status", "1");
        // List<TTopicInfo> topicInfoList=topicInfoService.findTopicInfoListByParams(param,null);
        param.put("article_status", "2");
        // 根据 topic 的 name 分组查询 topic 下有书本的 topic name 列表（topic 启用，article_status 发布）
        List<TTopicInfo> topicInfoList=topicInfoService.queryTopicList(param);
        Map<String, Object> result = new HashMap<>();
        result.put("topicInfoList", topicInfoList);
        return ResponseHandler.getResponse(result);
       }

    /**
     * 查询图书列表带分页
     * @param topicId
     * @param pageNum
     * @param pageSize
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "查询图书列表带分页",notes = "新增查询条件 gradeId")
    @RequestMapping(value = "getBookInfoListByParams",method = RequestMethod.GET)
    @ResponseBody
    public ResponseHandler getBookInfoListByParams(
            @ApiParam("话题编号")@RequestParam(name = "topicId",required = false)String topicId,
            @ApiParam("年级 id") @RequestParam(name = "gradeId",required = false)String gradeId,
            @ApiParam("页号")@RequestParam(name = "pageNum",required = true)Integer pageNum,
            @ApiParam("页面记录数")@RequestParam(name = "pageSize",required = true)Integer pageSize) throws Exception{
        Map<String, Object> param = new HashMap<String, Object>();
        String bookIds="";
        if(!"".equals(topicId)&&topicId!=null) {//查询某个话题的书
            param.put("topicid", topicId);
            List<TArticletopicRelation> articletopicRelationList = articleInfoService.findBookIdListByParams(param, null);
            for (TArticletopicRelation articletopicRelationObj : articletopicRelationList) {
                bookIds = bookIds + "'" + articletopicRelationObj.getArticleid() + "',";
            }
            bookIds = bookIds.length() > 0 ? bookIds.substring(0, bookIds.length() - 1) : "''";
            param = null;
            param = new HashMap<String, Object>();
            param.put("ids", bookIds);

        }
        param.put("status", "2");
        if(gradeId!=null && !"".equals(gradeId)){
            logger.debug("gradeId => " + gradeId);
            param.put("gradeId",gradeId);
            TGradeBook tGradeBook = gradeBookService.selectByParams(param);
            param.put("min_lexile",tGradeBook.getMinLexile());
            param.put("max_lexile",tGradeBook.getMaxLexile());
        }
        RollPage<TArticleInfo> rollPage=articleInfoService.findBookInfoListByParams(param, Order.asc("lexile"), pageNum, pageSize);
        param=null;
        param = new HashMap<String, Object>();
        param.put("userId", UserDetailHelper.getUserDetail().getUserId());
        List<VArticleInfo> VArticleInfoList=resourceService.setUserBookSign(param, rollPage.getResultList());
        RollPage<VArticleInfo> bookRollPage=new RollPage<VArticleInfo>(rollPage.getiTotalRecords(),rollPage.getiTotalDisplayRecords(),rollPage.getPageSize(),rollPage.getPageNum());
        bookRollPage.setResultList(VArticleInfoList);
        Map<String, Object> result = new HashMap<String, Object>();
        //result.put("resCode","0000");
        result.put("bookRollPage", bookRollPage);
        return ResponseHandler.getResponse(result);
    }
    @ApiOperation("查询我的图书列表带分页")
    @RequestMapping(value = "getMyBookInfoListByParams",method = RequestMethod.GET)
    @ResponseBody
    public ResponseHandler getMyBookInfoListByParams(@ApiParam("页号")@RequestParam(name = "pageNum",required = true)Integer pageNum,
                                                   @ApiParam("页面记录数")@RequestParam(name = "pageSize",required = true)Integer pageSize) throws Exception{
        Map<String, Object> param = new HashMap<String, Object>();
        String bookIds="";
        RollPage<TArticleInfo> rollPage=null;
        RollPage<VArticleInfo> bookRollPage=null;
        param.put("userId", UserDetailHelper.getUserDetail().getUserId());
        List<RUserBook> userBookL=resourceService.findUserBookList(param);
        if(userBookL.size()>0) {
            for (RUserBook userBookObj : userBookL) {
                bookIds = bookIds + "'" + userBookObj.getBookId() + "',";
            }
            bookIds = bookIds.length() > 0 ? bookIds.substring(0, bookIds.length() - 1) : "''";
            param = null;
            param = new HashMap<String, Object>();
            param.put("ids", bookIds);
            param.put("status", "2");
            rollPage=articleInfoService.findBookInfoListByParams(param, Order.asc("lexile"), pageNum, pageSize);
            param=null;
            param = new HashMap<String, Object>();
            param.put("userId", UserDetailHelper.getUserDetail().getUserId());
            List<VArticleInfo> vArticleInfoList=resourceService.setUserBookSign(param, rollPage.getResultList());
            Collections.sort(vArticleInfoList);
            bookRollPage=new RollPage<VArticleInfo>(rollPage.getiTotalRecords(),rollPage.getiTotalDisplayRecords(),rollPage.getPageSize(),rollPage.getPageNum());
            bookRollPage.setResultList(vArticleInfoList);
        }else{

        }
        Map<String, Object> result = new HashMap<String, Object>();
        //result.put("resCode","0000");
        result.put("bookRollPage", bookRollPage);
        return ResponseHandler.getResponse(result);
    }




}
