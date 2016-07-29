package com.langying.toolbox.utils;


import com.langying.toolbox.security.Security;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Liwei on 2016/3/20.
 *
 * 发送短信工具类
 * 说明：本工具类根据《创蓝短信网关接口规范文档》开发，网址：http://zz.253.com/index/index.html

 *
 */
public class SmsUtil {


    private static Logger logger = LoggerFactory.getLogger(SmsUtil.class);

    /**
     *
     * @param telephone 电话号码
     * @param verificationCode 验证码
     * @return 1：发送成功；0：发送失败
     */
    public static Integer sendVerificationCode(String telephone,String verificationCode){
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            String smInfo = "【有氧英语】您好，您的动态码是：" + verificationCode + "，3分钟内有效。如非您本人操作，可忽略本消息。";
            String smsUrl = "http://222.73.117.138:7891/mt?un=N13661921288&pw=251657&da=TELEPHONE&sm=SMINFO&dc=15&rd=1";
            smsUrl = smsUrl.replace("TELEPHONE",telephone);
            smsUrl = smsUrl.replace("SMINFO",Security.encodeHexString(smInfo,"gbk"));
            HttpGet httpget = new HttpGet(smsUrl);
            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                @Override
                public String handleResponse(
                        final HttpResponse response) throws IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        try {
                            return entity != null ? EntityUtils.toString(entity) : null;
                        } catch (ParseException ex) {
                            throw new ClientProtocolException(ex);
                        }
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };
            String responseBody = httpclient.execute(httpget, responseHandler);
            logger.info("responseBody => " + responseBody);
            if(responseBody.startsWith("r=")){
                return 0;
            }
            return 1;
        }catch (IOException ioException){
            ioException.printStackTrace();
        }
        return 0;
    }

}
