package com.jt.test.controller;


import com.jt.test.domain.Order;
import com.jt.test.helper.ThreadHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

/**
 * ThreadPoolController
 *
 * @author jt
 * @date 2022/4/26
 **/
@RestController
@Api(tags = "线程池创建测试")
@RequestMapping("/thread")
@Slf4j
public class ThreadPoolController {

    private final  Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ThreadHelper threadHelper;

    /**
     * 异步调用，无参
     * @throws InterruptedException
     */
    @GetMapping("")
    @ApiOperation("测试")
    public void threadTime1() throws InterruptedException {
        threadHelper.threadTime1();
        threadHelper.threadTime2();
        threadHelper.threadTime3();
        System.out.println("测试");
    }

    /**
     * 异步调用，接参
     */
    @Async("testThread")
    public List<Order> getlist() throws InterruptedException {

       List<Order> sumList = new ArrayList<>();
        List<Order> List1 = threadHelper.threadTime4();
        List<Order> List2 = threadHelper.threadTime5();
       sumList.addAll(List1);
       sumList.addAll(List2);
       return sumList;
    }
}
