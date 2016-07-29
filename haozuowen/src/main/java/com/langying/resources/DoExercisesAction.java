package com.langying.resources;


import com.langying.controller.common.UserDetailHelper;
import com.langying.controller.service.IDoExercisesService;

import com.langying.controller.service.IUserBookService;
import com.langying.exception.ApiException;
import com.langying.handler.ResponseHandler;
import com.langying.models.BookStatusInfo;
import com.langying.models.RUserBook;
import com.langying.models.UUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenxu on 2016/4/19.
 * 做习题
 */
@RestController
@Api("做习题")
@RequestMapping("/doExercises")
public class DoExercisesAction {

    @Autowired
    IDoExercisesService doExercisesService;

    @Autowired
    private IUserBookService userBookService;

    @ApiOperation(value = "通过书籍 ID 获取各个题目的类型以及状态（如果是已经完成的练习，显示分数）",notes = "阅读功能目录选择页 0:未开通,1:未解锁,2:已解锁",response = BookStatusInfo.class)
    @RequestMapping(value = "exercisesStatus", method = RequestMethod.GET)
    ResponseHandler exercisesStatus(@ApiParam("书籍ID")@RequestParam(name = "bookId",required = true)String bookId) throws Exception {
        BookStatusInfo bookStatusInfo = doExercisesService.exercisesStatus(bookId);
        return ResponseHandler.getResponse(bookStatusInfo);
    }

    /**

    @ApiOperation("通过书籍编号,习题序号获取习题")
    @RequestMapping(value = "question")
    ResponseHandler getQuestionBySeq(@ApiParam("书籍ID")@RequestParam(name = "bookId",required = true)String bookId,@ApiParam("题型")@RequestParam(name = "questionType",required = true)int questionType,
                                     @ApiParam("题目序号")@RequestParam(name = "seq",required = true)int seq) throws Exception {

        return ResponseHandler.getResponse();
    }
    @ApiOperation("保存答案")
    @RequestMapping(value = "answer")
    ResponseHandler answer(@ApiParam("习题编号")@RequestParam(name = "questionId",required = true)String questionId,@ApiParam("答案")@RequestParam(name = "answer",required = true)String answer
                                     ) throws Exception {

        return ResponseHandler.getResponse();
    }

    @ApiOperation("通过书籍编号,题目序号查询题目及答案")
    @RequestMapping(value = "questionAndAnswer")
    ResponseHandler questionAndAnswer(@ApiParam("书籍ID")@RequestParam(name = "bookId",required = true)String bookId,@ApiParam("题型")@RequestParam(name = "questionType",required = true)int questionType,
                                     @ApiParam("题目序号")@RequestParam(name = "seq",required = true)int seq) throws Exception {

        return ResponseHandler.getResponse();
    }


    @ApiOperation("做题结果查询")
    @RequestMapping(value = "exercisesEnd")
    ResponseHandler exercisesEnd(@ApiParam("书籍ID")@RequestParam(name = "bookId",required = true)String bookId,@ApiParam("题型")@RequestParam(name = "questionType",required = true)int questionType) throws Exception {

        return ResponseHandler.getResponse();
    }
    */
    @ApiOperation("进入某本书籍习题入口")
    @RequestMapping(value = "/enterBookExercises", method = RequestMethod.GET)
    @ResponseBody
    public ResponseHandler enterBookExercises(HttpServletRequest request,
                                          @ApiParam("书籍id") @RequestParam(value = "bookId", required = true) String bookId,
                                          @ApiParam("题目类型id(选择题2)") @RequestParam(value = "questionStyleId", required = true) String questionStyleId)
            throws Exception {
        Map<String, Object> result = new HashMap<>();
        if("2".equals(questionStyleId)){//单选题
            Map<String, Object> param = new HashMap<>();
            param.put("bookId", bookId);//
            param.put("isActive", "1");//当前做题轮次
            param.put("doStatus", "0");//未完成题
            param.put("userId", UserDetailHelper.getUserDetail().getUserId());
            Integer notFinish=doExercisesService.findCountByParams(param);
            if(notFinish>0){//当前轮次未完成
                result=doExercisesService.getCurrentExercises(bookId);
                result.put("doExercisesStatus", "1");
            }else{
                param = null;
                param = new HashMap<String, Object>();
                param.put("bookId", bookId);//
                param.put("userId", UserDetailHelper.getUserDetail().getUserId());
                Integer notDo=doExercisesService.findCountByParams(param);
                if(notDo<=0) {//开始做
                    result = doExercisesService.getNewExercises(bookId);
                    result.put("doExercisesStatus", "0");
                }else{//已经完成，查询结果
                    result = doExercisesService.getDoExercisesResult(bookId);
                    result.put("doExercisesStatus", "2");
                }
            }
        }else{

        }
        return ResponseHandler.getResponse(result);

    }


    /**
     * 用户做完一本书的习题以后，点击“重做”按钮触发
     * @param request
     * @param bookId
     * @param questionStyleId
     * @return
     * @throws Exception
     */
    @ApiOperation("重做书籍的习题")
    @RequestMapping(value = "/redoBookExercises", method = RequestMethod.GET)
    @ResponseBody
    public ResponseHandler redoBookExercises(HttpServletRequest request,
                                              @ApiParam("书籍id") @RequestParam(value = "bookId", required = true) String bookId,
                                              @ApiParam("题目类型id(选择题2)") @RequestParam(value = "questionStyleId", required = true) String questionStyleId)
            throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        if("2".equals(questionStyleId)){//单选题
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("bookId", bookId);//
            param.put("isActive", "1");//当前做题轮次
            param.put("doStatus", "0");//未完成题
            param.put("userId", UserDetailHelper.getUserDetail().getUserId());

            // 查询当前激活的题目中，还没有做完的题目数量
            Integer notFinish=doExercisesService.findCountByParams(param);
            if(notFinish>0){//当前轮次中有未完成的题目
                // 返回已经做完的题目的下一题（r_user_doquestion 表中 doStatus = 0 ，isActive = 1）
                result=doExercisesService.getCurrentExercises(bookId);
            }else{//开始重做
               result = doExercisesService.getNewExercises(bookId);

            }
        }else{

        }
        return ResponseHandler.getResponse(result);

    }
    @ApiOperation("通过书籍的练习id(问题id)查询问题选项")
    @RequestMapping(value = "/getExercisesOptionById", method = RequestMethod.GET)
    @ResponseBody
    public ResponseHandler getExercisesOptionById(HttpServletRequest request,
                                              @ApiParam("练习id(问题id)") @RequestParam(value = "exercisesId", required = true) String exercisesId)
            throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result=doExercisesService.getExercisesOptionById(exercisesId);

        return ResponseHandler.getResponse(result);

    }
    @ApiOperation("通过用户做题id查询用户已完成的某问题题干及选项")
    @RequestMapping(value = "/getFinishExercisesById", method = RequestMethod.GET)
    @ResponseBody
    public ResponseHandler getFinishExercisesById(HttpServletRequest request,
                                                  @ApiParam("用户做题id") @RequestParam(value = "userDoquestionId", required = true) Integer userDoquestionId)
            throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result=doExercisesService.getFinishExercisesById(userDoquestionId);

        return ResponseHandler.getResponse(result);

    }
    @ApiOperation("保存单选题的做题答案")
    @RequestMapping(value = "/saveExercisesAnswer", method = RequestMethod.GET)
    @ResponseBody
    public ResponseHandler saveExercisesAnswer(HttpServletRequest request,
                                                  @ApiParam("用户做题id") @RequestParam(value = "userDoquestionId", required = true) Integer userDoquestionId,
                                                  @ApiParam("用户选择的答案选项id") @RequestParam(value = "userOptionId", required = true) String userOptionId)
            throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result=doExercisesService.saveExercisesAnswer(userDoquestionId,userOptionId);

        return ResponseHandler.getResponse();

    }
    @ApiOperation("完成练习更改状态")
    @RequestMapping(value = "/updateUserBookStatus",method = RequestMethod.POST)
    @ResponseBody
    ResponseHandler updateUserBookStatus(@ApiParam("书籍ID")@RequestParam(name = "bookId",required = true)String bookId){
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
                userBook.setReadStatus("3");
                userBookService.modifyBasic(userBook);

                return  ResponseHandler.getResponse();
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseHandler.getResponseCon("0001","系统错误");
        }
    }
}
