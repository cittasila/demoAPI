package com.langying.resources;

import com.langying.controller.service.IHzwReportService;
import com.langying.exception.ApiException;
import com.langying.handler.ResponseHandler;
import com.langying.models.ResponseDesc;
import com.langying.models.UUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenxu on 2016/3/21.
 */
@RestController
@Api("上海好作文报告")
@RequestMapping("/hzwReport")
public class HzwReportAction {

    @Autowired
    IHzwReportService service;

    @ApiOperation(value = "报告",notes = "上海好作文报告",response = ResponseDesc.Report.class)
    @ApiResponse(code=200,message = "",reference = "{b:1}")
    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public ResponseHandler report(@ApiParam("token") @RequestHeader String token, @ApiParam("作业编号") @RequestParam(name = "assignmentId", required = true) Integer assignmentId) throws ApiException {
        Map map=service.getEssayList(token);
        if(map==null||map.size()==0){
            throw new ApiException("2009");
        }
        List<Map> list=(List<Map>)map.get("list");
        UUser user=(UUser) map.get("user");
        for(Map obj :list){
            if(obj.containsKey("assignmentId")&&obj.get("assignmentId").equals(assignmentId)){
                return ResponseHandler.getResponse(service.report(assignmentId, user));
            }
        }
        throw new ApiException("2011");
    }
}
