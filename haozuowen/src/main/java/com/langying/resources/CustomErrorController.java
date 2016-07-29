package com.langying.resources;

import io.swagger.annotations.Api;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenxu on 2015/11/26.
 */
@Controller
public class CustomErrorController implements  ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = "/error")
    @ExceptionHandler(Exception.class)
    @ResponseBody
	public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
		Map<String, Object> body = getErrorAttributes(request, getTraceParameter(request));
        Map<String, Object> map=new HashMap<String, Object>();
		HttpStatus status = getStatus(request);
        if(status!=HttpStatus.ACCEPTED){
            map.put("resCode","1001");
            map.put("status",body.get("status"));
            if("401".equals(map.get("status").toString())&&request.getHeader("authorization")==null){
                map.put("resCode","0401");
                map.put("resDesc","访问失效,请重新登录");
            }
                else
                map.put("resDesc",body.get("message"));
            status=HttpStatus.OK;
            return new ResponseEntity<Map<String, Object>>(map, status);
        }
		return new ResponseEntity<Map<String, Object>>(body, status);
	}
    private boolean getTraceParameter(HttpServletRequest request) {
        String parameter = request.getParameter("trace");
        if (parameter == null) {
            return false;
        }
        return !"false".equals(parameter.toLowerCase());
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request,
                                                   boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return new DefaultErrorAttributes().getErrorAttributes(requestAttributes,
                includeStackTrace);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        if (statusCode != null) {
            try {
                return HttpStatus.valueOf(statusCode);
            }
            catch (Exception ex) {
            }
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
    @Override
    public String getErrorPath() {
        return PATH;
    }
}