package com.jt.test.demo1.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * ThredPoolTaskExecutor
 * 线程池配置类(目前测试可用)
 *
 * @author jt
 * @date 2022/4/27
 **/
@Configuration
//开启异步调用
@EnableAsync
public class ThreadPoolCreator {
    private final Logger myLog = LoggerFactory.getLogger(this.getClass());

    private static ThreadPoolTaskExecutor executor;
    //核心线程数量
    private int corePoolSize = 2;
    //最大线程数量
    private int maxPoolSize = 5;
    //队列的长度
    private int queueSize = 10;

    //注意这里bean要设个名称，在后面使用异步的时候@Async也要带上配置bean的名称
    @Bean("testThread")
    public Executor myThreadPoolTaskExecutor() {
        myLog.info("开始创建线程池1");

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(maxPoolSize);
        executor.setCorePoolSize(corePoolSize);
        executor.setQueueCapacity(queueSize);
        //设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        //设置线程名前缀
        executor.setThreadNamePrefix("threadPoolTaskExecutor-》》》");
        //实例化执行
        executor.initialize();
        myLog.info("线程池1创建完成");
        return executor;
    }
}
