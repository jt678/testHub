package com.jt.test.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * AopConfig
 *
 * @author jt
 * @date 2022/5/17
 **/

@Configuration
//开启注解版的spring AOP功能，类似使用xml方式<aop:aspectj-autoproxy>
@EnableAspectJAutoProxy
public class AopConfig {


}
