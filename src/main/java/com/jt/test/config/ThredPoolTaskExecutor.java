package com.jt.test.config;

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
 * 线程池配置
 * @author jt
 * @date 2022/4/27
 **/
@Configuration
//开启异步调用
@EnableAsync
public class ThredPoolTaskExecutor {
    private final Logger myLog = LoggerFactory.getLogger(this.getClass());

    private int corePoolSize = 2;

    private int maxPoolSize = 5;

    private int queueSize = 10;

    @Bean("testThread")
    public Executor threadPoolTaskExecutor() {
        myLog.info("开始创建线程");

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(maxPoolSize);
        executor.setCorePoolSize(corePoolSize);
        executor.setQueueCapacity(queueSize);
        //设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        //设置线程名前缀
        executor.setThreadNamePrefix("threadPoolTaskExecutor-》》》");
        executor.initialize();
        return executor;
    }
}
