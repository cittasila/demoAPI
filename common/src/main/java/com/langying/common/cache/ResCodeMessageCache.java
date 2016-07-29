package com.langying.common.cache;

import groovy.util.Node;
import groovy.util.NodeList;
import groovy.util.XmlParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;


import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 */
public class ResCodeMessageCache implements InitializingBean{

    static final String Success="0000";

    private Resource[] codeXml;
    void setCodeXml(Resource[] codeXml) {
        this.codeXml = codeXml;
    }

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    static Map<String ,String> ResMessage = new HashMap<String, String>();

    public ResCodeMessageCache(Resource[] codeXml) {
        this.codeXml = codeXml;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("加载ResCode消息字典开始");
        for(Resource it : codeXml) {
            Node xmlMessage = new XmlParser().parse(it.getInputStream());
            for(Object node:(NodeList)xmlMessage.value()){
                Node node1=(Node)node;
                ResMessage.put(node1.attributes().get("code").toString(),node1.attributes().get("desc").toString());
            }
               /* xmlMessage.Message.each {
                    ResMessage. "${it.@code as String}" = it.@desc as String
*/
        }
        logger.info("加载ResCode消息字典结束");
    }

    /**
     * 根据code获取描述信息
     * @param resCode
     * @return
     */
    public static String getMessageDesc(String resCode){
        return ResMessage.get(resCode);
    }

    /**
     * 根据Exception 获取 异常code
     * @param e
     * @return
     */
    public static String getMessageCode(Exception e){
        String res;
        if (e.getMessage()!=null && e.getMessage()!=""){
            res = ResMessage.get(e.getMessage())!=null?e.getMessage():"9999";
        }else{
            res = "9999";
        }
        return res;

    }

    /**
     * 根据Exception 获取 异常code
     * @param e
     * @param defaultCode
     * @return
     */
    static String getMessageCode(Exception e,String defaultCode){

        String errCode = getMessageCode(e);
        if (errCode == "9999") errCode = defaultCode;

       return errCode;

    }

}
