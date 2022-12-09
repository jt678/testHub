package com.jt.test.demo1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * MyApplicationContextInitializer
 * 自定义初始化容器,实现 ApplicationContextInitializer接口
 * springboot自带初始化容器，在jar包的 spring-boot-autoconfigure 项目 和 spring-boot 项目里面各有一个
 * 在META-INF/spring.factories 配置文件中，org.springframework.context.ApplicationContextInitializer 接口就是初始化器了
 * @Author: jt
 * @Date: 2022/12/8 13:48
 */

public class MyApplicationContextInitializer implements ApplicationContextInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        ConfigurableListableBeanFactory beanFactory = configurableApplicationContext.getBeanFactory();

        System.out.println("=======================================我是自定义的ApplicationContextInitializer=======================================");
        System.out.println("=======================================初始化开始=======================================");

          //最后在启动类springApplication中添加此初始化容器即可
        //自定义监听器相同步骤
    }
}

