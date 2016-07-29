package com.langying.aspect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.langying.toolbox.utils.*;
import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.langying.controller.common.UserDetailHelper;
import com.langying.models.UUser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.langying.toolbox.security.Security;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * Created by chenxu on 2015/12/1.
 */
@Configuration
@Aspect
class LogAspect {
    private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    private String requestPath = null ; // 请求地址
    private String userName = null ; // 用户名
    private Map<?, ?> inputParamMap = null ; // 传入参数
    private Object output = null; // 存放输出结果
    private long startTimeMillis = 0; // 开始时间
    private long endTimeMillis = 0; // 结束时间
    private String id;

    @Pointcut("execution(* com.langying.resources..*.*(..))||execution(* com.langying.exception.GlobalExceptionHandler.*(..))" +
            "||@within(org.springframework.web.bind.annotation.RestController)||@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void excudeLogService(){}
    /*
     * 方法调用前触发
     * @param joinPoint
       */
    @Before("excudeLogService()")
    public void doBeforeInServiceLayer(JoinPoint joinPoint) {
        startTimeMillis = System.currentTimeMillis(); // 记录方法开始执行的时间
    }

    /*
     *
     * @Title：doAfterInServiceLayer
     * @Description: 方法调用后触发
     *  记录结束时间
     * @param joinPoint*/

    @After("excudeLogService()")
    public void doAfterInServiceLayer(JoinPoint joinPoint) {
        endTimeMillis = System.currentTimeMillis(); // 记录方法执行完成的时间
        this.printOptLog();
    }



    /*
     *
     * @Title：doAround
     * @Description: 环绕触发
     * @return
     * @throws Throwable
        */
    @Around("excudeLogService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        /* *
        * 1.获取request信息
                * 2.根据request获取session
                * 3.从session中取出登录用户信息*/

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes)ra;
        if(sra!=null) {
            output=null;
            id= Security.MD5(UUID.randomUUID().toString());
            HttpServletRequest request = sra.getRequest();
            try {
                UUser userBasic = UserDetailHelper.getUserDetail();
                userName = userBasic.getUserRealName()!=null ?userBasic.getUserRealName(): userBasic.getUserName();
                userName=userName+"("+userBasic.getToken()+")";
            } catch (Exception e) {
                userName = "用户未登录";
            }
            // 获取输入参数
            inputParamMap = getParameterMap(request);
            // 获取请求地址
            requestPath = request.getRequestURI();
            ObjectMapper om=new ObjectMapper();
            Object logId=request.getAttribute("logId");
            if(logId==null){
                String ip=IpUtil.getIpAddr(request);
                request.setAttribute("logId",id);
                request.getSession().setAttribute("ip",ip);
                requestPath=request.getAttribute("path").toString();
                logger.info(id+"ip:" +ip+"地址:"+requestPath+" method:"+request.getMethod()+" 用户:"+userName+" 参数:"+om.writeValueAsString(inputParamMap));
            }else {
                id=logId.toString();
            }
        }
        // 执行完方法的返回值：调用proceed()方法，就会触发切入点方法执行
        Object result = pjp.proceed();// result的值就是被拦截方法的返回值
        output=result;


        return result;
    }









    private void printOptLog() {
        if(requestPath==null)return;
        ObjectMapper om=new ObjectMapper();
        String optTime= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTimeMillis);
        if(output!=null)
            try {
                logger.info(id+" 返回数据："+om.writeValueAsString(output)+ "开始时间:"+optTime+" 用时:"+(endTimeMillis-startTimeMillis)+"ms");
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        return;
    }

    public static Map getParameterMap(HttpServletRequest request) {
        // 参数Map
        Map properties = request.getParameterMap();
        // 返回值Map
        Map returnMap = new HashMap();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if(null == valueObj){
                value = "";
            }else if(valueObj instanceof String[]){
                String[] values = (String[])valueObj;
                for(int i=0;i<values.length;i++){
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length()-1);
            }else{
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }



}
