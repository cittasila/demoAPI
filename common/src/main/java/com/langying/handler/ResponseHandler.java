package com.langying.handler;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.langying.common.contant.CommonConstant;
import com.langying.common.cache.ResCodeMessageCache;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

/**
 * Created by chenxu on 2015/9/13.
 *
 */
@ApiModel(value="响应处理", description="处理响应,返回统一格式")
@JsonIgnoreProperties
public class ResponseHandler<T> implements Serializable {
    private static final long serialVersionUID = 3863559687276427577L;
    public static interface ResponseWrapperView{};

    @JsonView(ResponseWrapperView.class)
    @ApiModelProperty(value="返回码")
    private String resCode;

    @JsonView(ResponseWrapperView.class)
    @ApiModelProperty(value="返回信息")
    private String resMsg;

    @JsonView(ResponseWrapperView.class)
    private Object data;
    @JsonView(ResponseWrapperView.class)
    private String timestamp;
    @JsonIgnore
    final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    ResponseHandler(String resCode, String resMsg) {
        this.resCode = resCode;
        this.timestamp =String.valueOf(System.currentTimeMillis());
        this.resMsg=(resMsg==null||resMsg=="")?ResCodeMessageCache.getMessageDesc(resCode):resMsg;

    }

    ResponseHandler(String resCode, Object data) {
        this.resCode = resCode;
        this.data = data;
        this.timestamp =String.valueOf(System.currentTimeMillis());
        this.resMsg=ResCodeMessageCache.getMessageDesc(resCode);
    }

    ResponseEntity<ResponseHandler> build(HttpStatus code) {
        return new ResponseEntity<ResponseHandler>(this, HttpStatus.OK);
    }
 /*   ResponseEntity<ResponseWrapper> build() {
        HttpStatus status;
       // if(this.resCode==CommonConstant.RE_SUCCESS_CODE)
       status=HttpStatus.OK;
        //else
        //    status=HttpStatus.BAD_REQUEST
        logger.info("return:"+new ObjectMapper().writeValueAsString(this))
        return new ResponseEntity<ResponseWrapper>(this, status);
    }*/


    static public ResponseHandler getResponse(){
        return new ResponseHandler(CommonConstant.RE_SUCCESS_CODE,"");
    }
    static public ResponseHandler getResponse(Object  data){
        return new ResponseHandler(CommonConstant.RE_SUCCESS_CODE,data);
    }

    static public ResponseHandler getResponseCon(Object  data){
        return new ResponseHandler(CommonConstant.RE_SUCCESS_CODE,data);
    }

    static public ResponseHandler getResponseCon(String resCode,String resMsg){
        return new ResponseHandler(resCode,resMsg);
    }

    static public ResponseHandler getResponseCon(String resCode,Object obj){
        return new ResponseHandler(resCode,obj);
    }

    static public ResponseEntity<ResponseHandler> getResponse(String msg, String code){
        return new ResponseHandler(code,msg).build(HttpStatus.OK);
    }

    String getResCode() {
        return resCode;
    }

    void setResCode(String resCode) {
        this.resCode = resCode;
    }

    Object getData() {
        return data;
    }

    void setData(Object data) {
        this.data = data;
    }

    String getTimestamp() {
        return timestamp;
    }

    void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
