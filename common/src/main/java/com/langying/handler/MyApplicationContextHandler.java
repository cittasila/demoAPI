package com.langying.handler;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by a on 2015/9/13.
 */
@Component
public class MyApplicationContextHandler implements ApplicationContextAware {
    private static ApplicationContext context;//声明一个静态变量保存

    public void setApplicationContext(ApplicationContext arg0)throws BeansException {
        context=arg0;
    }

    public static Object getBean(String beanName){
        return context.getBean(beanName);
    }

    public static<T> T getBean(Class<T> c) {
        return context.getBean(c);
    }

}
