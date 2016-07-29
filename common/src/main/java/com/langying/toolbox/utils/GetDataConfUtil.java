/**
 * TODO(功能：获取配置文件信息)
 *
 * @title: GetDateConfUtil.java
 * @author WANGXY
 * @date 2013-11-19 10:25:40
 */
package com.langying.toolbox.utils;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class GetDataConfUtil.
 * 
 * @className: GetDataConfUtil
 * @author GEYIYI
 * @date 2013-11-21 10:09:10
 * @version V1.0 TODO(如果是修改版本，描述修改内容)
 */
public class GetDataConfUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(GetDataConfUtil.class);
    
    /**
	 * Instantiates a new gets the date conf util.
	 */
    private GetDataConfUtil()
    {
    }

    /**
	 * Gets the bundle.
	 * 
	 * @param fileName
	 *            the file name
	 * @return the bundle
	 */
    public static ResourceBundle getBundle(String fileName)
    {
        return getBundle(fileName, false);
    }

    /**
	 * Gets the bundle.
	 * 
	 * @param fileName
	 *            the file name
	 * @param needSeparateEnv
	 *            the need separate env
	 * @return the bundle
	 */
    public static ResourceBundle getBundle(String fileName, boolean needSeparateEnv)
    {
        String fullFileName = fileName;
        if (needSeparateEnv)
        {
            String suffix = getFileSuffix();
            fullFileName = (new StringBuilder(String.valueOf(fullFileName))).append("_")
                    .append(suffix).toString();
        }
        return ResourceBundle.getBundle(fullFileName);
    }
    
    /**
	 * 获取指定配置文件中的key值.
	 * 
	 * @param fileName
	 *            文件名称
	 * @param key
	 *            文件中key值
	 * @return the property value
	 */
    public static String getPropertyValue(String fileName, String key)
    {
        return getPropertyValue(fileName, key, false);
    }

    /**
	 * Gets the property value.
	 * 
	 * @param fileName
	 *            the file name
	 * @param key
	 *            the key
	 * @param needSeparateEnv
	 *            the need separate env
	 * @return the property value
	 */
    public static String getPropertyValue(String fileName, String key, boolean needSeparateEnv)
    {
        String fullFileName = fileName;
        if (needSeparateEnv)
        {
            String suffix = getFileSuffix();
            fullFileName = (new StringBuilder(String.valueOf(fullFileName))).append("_")
                    .append(suffix).toString();
        }
        logger.info(fileName +"文件中捕获"+"'"+key+"'"+"的值"+"'"+ResourceBundle.getBundle(fullFileName).getString(key)+"'");
        return ResourceBundle.getBundle(fullFileName).getString(key);
    }
    
    /**
	 * Checks if is exist key.
	 * 
	 * @param fileName
	 *            the file name
	 * @param key
	 *            the key
	 * @return true, if is exist key
	 */
    public static boolean isExistKey(String fileName, String key){
    	 return ResourceBundle.getBundle(fileName).containsKey(key);
    }

    /**
	 * Gets the file suffix.
	 * 
	 * @return the file suffix
	 */
    private static String getFileSuffix()
    {
    	return System.getProperty(""); 
    }

    /**
	 * Clear cache.
	 */
    public static void clearCache()
    {
        ResourceBundle.clearCache();
    }

    /**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
    public static void main(String[] args)
    {	
    	GetDataConfUtil.getPropertyValue("sys_config", "errorPercent");
        GetDataConfUtil.getPropertyValue("data_config", "apps.report.studentreport.title.printall");
        System.out.println(GetDataConfUtil.isExistKey("condition_code", "Suspect_Entropy"));
    }
}
