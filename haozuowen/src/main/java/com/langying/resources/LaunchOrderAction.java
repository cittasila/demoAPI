package com.langying.resources;

import com.langying.controller.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by thtanghao on 2016/4/11.
 */
@Component
public class LaunchOrderAction implements CommandLineRunner {

    private static Logger logger  = LoggerFactory.getLogger(LaunchOrderAction.class);

    public LaunchOrderAction() {
        //System.out.println("InitSequenceBean: constructor");
    }

    @PostConstruct
    public void postConstruct() {
        //System.out.println("InitSequenceBean: postConstruct");
    }


    @Autowired
    private IOrderService orderService;
    @Override
    public void run(String... args) throws Exception {
        logger.info(">>>>>>>>>>>>>>>开始自动检查所有未完成的订单 <<<<<<<<<<<<<");
        orderService.checkNotFinishOrder();
        logger.info(">>>>>>>>>>>>>>>自动检查所有未完成的订单结束 <<<<<<<<<<<<<");
    }
}
