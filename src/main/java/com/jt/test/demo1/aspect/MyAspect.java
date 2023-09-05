package com.jt.test.demo1.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * MyAspect
 *
 * @Author: jt
 * @Date: 2023/7/11 9:34
 */
@Component
@Aspect
public class MyAspect {

    @Pointcut("execution(* com.jt.test.demo1.controller.*.*(..)) && @annotation(com.jt.test.demo1.annotation.MyAnnotation)")
    public void myPointCut() {
    }


    //前置通知
    @Before("myPointCut()")
    public void doBefore(JoinPoint jp) {
        System.out.println(jp.getSignature().getName()+"前置通知");
    }

    //返回通知
    @AfterReturning(value = "myPointCut()",returning = "result")
    public void doReturn(JoinPoint jp,Object result) {
        System.out.println(jp.getSignature().getName()+"返回通知,返回值是"+result);
    }

    //异常通知
    @AfterThrowing(value = "myPointCut()",throwing = "e")
    public void doThrow(JoinPoint jp,Exception e) {
        System.out.println(jp.getSignature().getName()+"异常通知:"+e.getMessage());
    }

    //后置通知
    @After("myPointCut()")
    public void doAfter(JoinPoint jp) {
        System.out.println(jp.getSignature().getName()+"后置通知");
    }



    //环绕通知
    @Around("myPointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println(pjp.getSignature().getName()+"方法环绕通知开始");
        //方法执行计时
        long start = System.nanoTime();
        Object proceed = pjp.proceed();
        Duration costTime = Duration.ofNanos(System.nanoTime() - start);
        System.out.println(pjp.getSignature().getName()+"方法环绕通知结束，执行时间 = " + costTime);
        return proceed;
    }
}
