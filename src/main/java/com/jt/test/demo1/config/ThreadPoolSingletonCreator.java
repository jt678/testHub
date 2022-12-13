package com.jt.test.demo1.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * ThreadPoolSingletonCreator
 *
 * @Author: jt
 * @Date: 2022/12/13 10:01
 */
@Slf4j
@EnableAsync
public class ThreadPoolSingletonCreator {
    private static ThreadPoolTaskExecutor executor;
    //核心线程数量
    private static final int corePoolSize = 2;
    //最大线程数量
    private static final int maxPoolSize = 5;
    //队列的长度
    private static final int queueSize = 10;

    //与configuration注解冲突
    private ThreadPoolSingletonCreator() {
    }

    //单例模式获取（DCL双检查机制）,问题是spring的bean默认都是单例，这样再加一层真的有实际作用嘛
    //注意这里bean要设个名称，在后面使用异步的时候@Async也要带上配置bean的名称
    @Bean
    public static ThreadPoolTaskExecutor getInstance() {

        if (executor == null) {
            synchronized (ThreadPoolSingletonCreator.class) {
                if (executor == null) {
                    log.info("开始创建线程池3");
                    executor = new ThreadPoolTaskExecutor();
                    executor.setMaxPoolSize(maxPoolSize);
                    executor.setCorePoolSize(corePoolSize);
                    executor.setQueueCapacity(queueSize);
                    //设置拒绝策略
                    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
                    //设置线程名前缀
                    executor.setThreadNamePrefix("threadPoolTaskExecutor3-》》》");
                    //实例化执行
                    executor.initialize();
                    log.info("线程池3创建完成");

                }
            }
        }
        return executor;
    }
}
