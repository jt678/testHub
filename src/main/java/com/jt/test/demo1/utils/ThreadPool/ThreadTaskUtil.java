package com.jt.test.demo1.utils.ThreadPool;

import com.jt.test.demo1.config.ThreadPoolSingletonCreator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * ThreadTaskUtil
 * 处理任务的线程工具类
 *
 * @Author: jt
 * @Date: 2022/12/13 10:05
 */
public class ThreadTaskUtil {
    private static ThreadPoolTaskExecutor poolTaskExecutor = ThreadPoolSingletonCreator.getInstance();

    public static String execute(Runnable command) throws ExecutionException, InterruptedException {

        String  result = "";
        //使用submit提交任务，execute()方法不接收callable线程任务
        poolTaskExecutor.execute(command);
        String name = Thread.currentThread().getName();
        result = name;
        return result;
    }
}
