package com.jt.test.demo1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

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
