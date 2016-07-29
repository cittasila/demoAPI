package com.langying.resources;

import com.langying.common.contant.CommonConstant;
import com.langying.controller.service.ICommonService;
import com.langying.controller.service.IUserManageService;
import com.langying.controller.service.IUserService;
import com.langying.exception.ApiException;
import com.langying.handler.ResponseHandler;
import com.langying.models.UUser;
import com.langying.toolbox.utils.ReadExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by chenxu on 2016/3/15.
 */
@RestController
@Api("用户管理相关信息")
@RequestMapping("/userManage")
public class UserManageAction {



    @Autowired
    IUserService userService;

    @Autowired
    ICommonService areaService;

    @Autowired
    IUserManageService userManageService;

    @ApiOperation("根据地区编号获取学校列表")
    @RequestMapping(value = "/school",method = RequestMethod.GET)
    @ResponseBody
    ResponseHandler school(@ApiParam("区域编号")@RequestParam(name = "cityCode",required = false)String cityCode){
        return ResponseHandler.getResponse(CommonConstant.getResMap("list",areaService.getSchool(cityCode)));
    }

    @ApiOperation("获取地区列表")
    @RequestMapping(value = "/city",method = RequestMethod.GET)
    @ResponseBody
    ResponseHandler userInfo(@ApiParam("区域编号")@RequestParam(name = "code",required = false)String code){
        Map map= CommonConstant.getListMap(areaService.getArea(code));
        return ResponseHandler.getResponse(map);
    }

    @ApiOperation("根据地区编号获取学校列表")
    @RequestMapping(value = "/getTeachersBySchoolId",method = RequestMethod.GET)
    @ResponseBody
    ResponseHandler getTeachersBySchoolId(@ApiParam("学校id")@RequestParam(name = "schoolId",required = true)String schoolId){
        List<UUser> userList= userService.queryTeacherBySchoolId(schoolId);
        List<Map> userMapList=new ArrayList<Map>();
        for(UUser uUser:userList){
            userMapList.add(CommonConstant.getResMap("id",uUser.getUserId(),"name",uUser.getUserRealName(),
                    "teachClassNames",uUser.getClassesName(),"userName",uUser.getUserName()));
        }
        return ResponseHandler.getResponse(CommonConstant.getListMap(userMapList));
    }

    @ApiOperation("根据教师查询学生")
    @RequestMapping(value = "/getStudentsByTeacherId",method = RequestMethod.GET)
    @ResponseBody
    ResponseHandler getStudentsByTeacherId( @ApiParam("教师id")@RequestParam(name = "teacherId",required = true)String teacherId){
        List<UUser> userList= userService.queryStudentByTeacherId(teacherId);
        List<Map> userMapList=new ArrayList<Map>();
        for(UUser uUser:userList){
            userMapList.add(CommonConstant.getResMap("id",uUser.getUserId(),"name",uUser.getUserRealName(),
                    "className",uUser.getClassesName(),"userName",uUser.getUserName(),"gradeName",uUser.getGradeName()));
        }
        return ResponseHandler.getResponse(CommonConstant.getListMap(userMapList));
    }

    @ApiOperation("导入指定学校教师")
    @RequestMapping(value = "/apiTeacherImport",method = RequestMethod.POST)
    @ResponseBody
    ResponseHandler apiTeacherImport(HttpServletRequest request,
                     @ApiParam("学校id")@RequestParam(name = "schoolId",required = true)Integer schoolId,
                     @ApiParam("登录名")@RequestParam(name = "userName",required = true)String userName,
                     @ApiParam("用户类型")@RequestParam(name = "userType",required = false)String userType,
                     @ApiParam("文件")@RequestParam(name = "imgurlfiledate")MultipartFile file
    ) throws ApiException{
        String[][] result=dealFileToList(file);
        int count=userManageService.dealUser(result,"42",schoolId,userName,userType);
        String msg="已导入"+count+"条数据";
        if(count==0)msg=msg+",请检查是否有完全重复的内容";
        return ResponseHandler.getResponseCon(CommonConstant.RE_SUCCESS_CODE,msg);
    }

    @ApiOperation("导入指定学校学生")
    @RequestMapping(value = "/apiStudentImport",method = RequestMethod.POST)
    @ResponseBody
    ResponseHandler apiStudentImport(HttpServletRequest request,
                     @ApiParam("学校id")@RequestParam(name = "schoolId",required = true)Integer schoolId,
                     @ApiParam("登录名")@RequestParam(name = "userName",required = true)String userName,
                     @ApiParam("用户类型")@RequestParam(name = "userType",required = false)String userType,
                    @ApiParam("文件路径")@RequestParam(name = "imgurlfiledate")MultipartFile file
    ) throws ApiException{
        String[][] result=dealFileToList(file);
        int count=userManageService.dealUser(result,"38",schoolId,userName,userType);
        String msg="已导入"+count+"条数据";
        if(count==0)msg=msg+",请检查是否有完全重复的内容";
        return ResponseHandler.getResponseCon(CommonConstant.RE_SUCCESS_CODE,msg);
    }

    /**
     * 将上传的文件转换成list
     * @param file
     * @return
     * @throws ApiException
     */
    private String[][] dealFileToList(MultipartFile file) throws ApiException{
        if (!file.isEmpty()) {
            String[][] result={};
            try {
                if(file.getOriginalFilename().endsWith("xls")){
                    result = ReadExcelUtil.read2003ExcelStandard(file.getInputStream(), 4);
                }else if(file.getOriginalFilename().endsWith("xlsx")){
                    result = ReadExcelUtil.read2007ExcelStandard(file.getInputStream(),  4);
                }
               return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        throw new ApiException("2002");
    }
}
