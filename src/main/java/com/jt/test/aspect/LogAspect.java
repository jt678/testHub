package com.jt.test.aspect;

import com.jt.test.annotation.Log;
import org.apache.xmlbeans.ThreadLocalUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * LogAspect
 *
 * 操作日志记录处理
 * @author jt
 * @date 2022/5/17
 **/
@Aspect
@Component("LogAspect")
public class LogAspect {
    /**
     * 排除敏感字段
     */
    public static final String[] SENCITIVE_WORDS = {"password", "oldPassword", "newPassword", "confirmPassword"};
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    //切点配置，配置切面生效的作用域
    @Pointcut("@annotation(com.jt.test.annotation.Log)")
    public void logPointCut(){

    }

    /**
     * 目标方法执行前执行
     */
    @Before(value = "@annotation(myLog)")
    public void doBeforeLog(JoinPoint joinPoint,Log myLog){
        //获取目标方法参数
        Object[] args = joinPoint.getArgs();
        //获取目标方法
        Signature signature = joinPoint.getSignature();
        //获取ioc容器内的目标对象
        Object target = joinPoint.getTarget();

        String name = signature.getName();
        System.out.println("切面方法"+getClass().getSimpleName()+"目标方法名："+name);

    }

    /**
     * 目标方法执行后执行
     */
    @After(value = "@annotation(myLog)",argNames = "joinPoint,myLog")
    public  void  doAfterLog(JoinPoint joinPoint,Log myLog){
        Signature signature = joinPoint.getSignature();
        Class declaringType = signature.getDeclaringType();
        String declaringTypeName = signature.getDeclaringTypeName();
        System.out.println(declaringType);
        System.out.println(declaringTypeName);

    }

    /**
     * 处理完请求执行
     */
    @AfterReturning(value = "@annotation(myLog)",  returning= "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Log myLog,Object jsonResult){
        logHandle(joinPoint,myLog,null,jsonResult);
    }

    /**
     * 拦截异常操作
     */
    @AfterThrowing(value = "@annotation(myLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint,Log myLog,Exception e){
        logHandle(joinPoint,myLog,e,null);
    }

    private void logHandle(final JoinPoint joinPoint, Log myLog,final Exception e,Object jsonResult) {
//        log处理逻辑
    }

    /**
     * Around执行
     */




}
