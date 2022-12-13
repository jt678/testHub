package com.jt.test.demo1.utils.ThreadPool;

import lombok.SneakyThrows;

import java.util.concurrent.Callable;

/**
 * MyThreadTask
 * 线程任务类
 * @Author: jt
 * @Date: 2022/12/13 10:15
 */
public class MyThreadTask implements Runnable {
    //模拟要处理的数据
    private final String data;

    public MyThreadTask(String data) {
        this.data = data;
    }

    @SneakyThrows
    @Override
    public void run() {
        System.out.println("==============正在处理收到的data："+ data);
        Thread.sleep(1000);
    }


//    @Override
//    public Object call() throws Exception {
//        System.out.println("==============正在处理收到的data："+ data);
//        //模拟数据处理
//        Thread.sleep(5000);
//        return "处理成功";
//    }
}
