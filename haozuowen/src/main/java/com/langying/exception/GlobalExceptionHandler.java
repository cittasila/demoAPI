package com.langying.exception;

import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by god on 2015/3/6.
 */
@ControllerAdvice(annotations = RestController.class)
class GlobalExceptionHandler {
    static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private Environment env;

    @ExceptionHandler(Exception.class)
    @ResponseBody
    Map Exception(Exception ex) {
        logger.error(ex.getMessage(),ex);
        String message="";
        if(ex instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException missing =(MissingServletRequestParameterException) ex;
            ex=new ApiException("2001",missing.getParameterName()+"必填");
        }else
        if(ex instanceof MethodArgumentNotValidException||ex instanceof BindException){
            BindingResult bindingResult=(ex instanceof MethodArgumentNotValidException?((MethodArgumentNotValidException)ex).getBindingResult():((BindException)ex).getBindingResult());
            List<ObjectError> FieldErrors=bindingResult.getAllErrors();
            for(ObjectError objectError:FieldErrors){
                FieldError oe=(FieldError)objectError;
                ApiModelProperty apiModelProperty= null;
                try {
                    apiModelProperty = bindingResult.getTarget().getClass().getDeclaredField(oe.getField()).getAnnotation(ApiModelProperty.class);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                if(apiModelProperty!=null){
                    String desc=apiModelProperty.value();
                    if(desc!=null){
                        message=message+" "+desc+oe.getDefaultMessage();
                    }
                }else {
                    message=message+" "+oe.getDefaultMessage();
                }
                message=message.length()==0?(oe.getField()+oe.getDefaultMessage()):message;
            }
            ex=new ApiException("2001",message.trim());
        }

        return ApiException.getResPonse(ex);
    }


    @RequestMapping(value = "/error")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
	/*	Map<String, Object> body = getErrorAttributes(request, getTraceParameter(request));
		HttpStatus status = getStatus(request);*/

		return  null;
    }

}

