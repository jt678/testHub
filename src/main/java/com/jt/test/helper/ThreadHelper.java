package com.jt.test.helper;

import com.jt.test.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * ThreadHelper
 *
 * @author jt
 * @date 2022/4/26
 **/
@Service
public class ThreadHelper {

    private final Logger myLog = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private OrderService service;

    @Resource(name = "threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public void threadTime() {
        Date startTime = new Date();
        threadPoolTaskExecutor.execute(() -> {
            service.list();
            Date endTime = startTime;
        });



        Date endTime = new Date();
        Long finalTime = endTime.getTime()-startTime.getTime();
        System.out.println("cost ：" +finalTime);

    }

    @Async("testThread")
    public void threadTime1() throws InterruptedException {

        myLog.info("方法1开始");
        String name = Thread.currentThread().getName();
        System.out.println(name);
        service.list();
        System.out.println("方法1执行方法中");
        myLog.info("方法1结束");
    }
    @Async("testThread")
    public void threadTime2() throws InterruptedException {

        myLog.info("方法2开始");
        String name = Thread.currentThread().getName();
        System.out.println(name);
        service.list();
        System.out.println("方法2执行方法中");
        myLog.info("方法2结束");
    }

    @Async("testThread")
    public void threadTime3() throws InterruptedException {

        myLog.info("方法3开始");
        String name = Thread.currentThread().getName();
        System.out.println(name);
        service.list();
        System.out.println("方法3执行方法中");
        myLog.info("方法3结束");
    }

}
