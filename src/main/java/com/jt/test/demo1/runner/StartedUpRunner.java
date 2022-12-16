package com.jt.test.demo1.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * StartedUpRunner
 * 启动状态监视/重新run方法
 * @Author: jt
 * @Date: 2022/12/8 14:55
 */
@Component
public class StartedUpRunner implements ApplicationRunner {

    private final ConfigurableApplicationContext context;

    public StartedUpRunner(ConfigurableApplicationContext context) {
        this.context = context;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //如果程序启动正常
        if (context.isActive()){
            //获取环境配置
            ConfigurableEnvironment environment = context.getEnvironment();
            //这两个是主机系统信息Map
            Map<String, Object> systemEnvironment = environment.getSystemEnvironment();
            Map<String, Object> systemProperties = environment.getSystemProperties();
            //输出到终端

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("================jt================");
            System.out.println("服务启动，时间："+sdf.format(new Date()));
            System.out.println("Java："+systemEnvironment.get("JAVA_HOME"));
            System.out.println("操作系统："+systemProperties.get("os.name"));
            System.out.println("CPU架构："+systemEnvironment.get("PROCESSOR_ARCHITECTURE"));
            System.out.println("服务名称："+environment.getProperty("spring.application.name"));
            System.out.println("服务端口号："+environment.getProperty("server.port"));
            System.out.println("================jt================");
        }


    }
}
