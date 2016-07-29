import com.langying.toolbox.utils.MD5Util;
import groovy.transform.ASTTest;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Liwei on 2016/3/31.
 */
public class UserLoginTest {


    /**
     * 用户登录请求编码
     * 加密规则：
     * 1、将用户名和密码以 “userName + ":" + password” 的方式拼接成字符串；
     * 2、将第 1 步得到的字符串进行 base64 编码；
     * 3、最后将第 2 步 返回的字符串前面加上 "Basic "；
     * 4、将第 3 步得到的字符串作为请求头 authorization 的值，进行请求。
     */
    @Test()
    public void test01(){
        String userName = "lirui123";
        String password = "123456";
        String str = userName + ":" + password;
        // 加密
        byte[] b = Base64.encodeBase64(str.getBytes(), true);
        String str1 = new String(b);
        System.out.println(str1);
        String result = "Basic " + str1;
        System.out.println("把下面一行全部复制到请求头 authorization 对应的值里。");
        System.out.println(result);// 请求头名字 authorization
    }

    @Test()
    public void test02(){
        String userName = "zhumei";
        String password = "meimei";
        String str = userName + ":" + password;
        // 加密
        byte[] b = Base64.encodeBase64(str.getBytes(), true);
        String str1 = new String(b);
        System.out.println(str1);
        String result = "Basic " + str1;
        System.out.println("把下面一行全部复制到请求头 authorization 对应的值里。");
        System.out.println(result);// 请求头名字 authorization
    }

    /**
     * 朗鹰作业接口测试
     */
    @Test
    public void test03(){
        String loginName="zhumei";
        String password ="meimei";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
        String postTime = sdf1.format(new Date());
        System.out.println("postTime 参数 => " + postTime);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        String date =sdf2.format(new Date());
        String miyao = "tokjbhw65n46qptxci";
        String checkString = date + postTime + loginName + password + miyao;
        String pkey = MD5Util.getMD5Str(checkString);
        System.out.println("pkey 参数 => "  + pkey);
    }



    @Test()
    public void a(){
        System.out.println( UUID.randomUUID().toString().replace("-",""));
    }

    @Test
    public void count(){
        Integer num1 = 2;
        Integer num2 = 7;
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        String result = numberFormat.format((float) num1 / (float) num2 * 100);
        System.out.println(result);
        System.out.println("num1和num2的百分比为:" + result + "%");
    }

}
