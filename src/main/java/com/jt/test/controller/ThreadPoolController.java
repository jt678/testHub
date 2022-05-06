package com.jt.test.controller;

import com.jt.test.domain.vo.OrderVO;
import com.jt.test.helper.ThreadHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * ThreadPoolController
 *
 * @author jt
 * @date 2022/4/26
 **/
@RestController
@Api(tags = "线程池创建测试")
@RequestMapping("/thread")
public class ThreadPoolController {

    @Autowired
    private ThreadHelper threadHelper;

    @GetMapping("")
    @ApiOperation("测试")


    public void threadTime1() throws InterruptedException {
        threadHelper.threadTime1();
        threadHelper.threadTime2();
        threadHelper.threadTime3();
        System.out.println("测试");
    }

}
