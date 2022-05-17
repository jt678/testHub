package com.jt.test.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * SimpleAspect
 *
 * @author jt
 * @date 2022/5/17
 **/
@Aspect
@Component
public class SimpleAspect {
    /**
     * 定义切入点,注意*和 com之间有空格
     */
    @Pointcut("execution(* com.jt.test.service.impl.SimpleAspectTestServiceImpl.*(..))")
    public void pointCut(){

    }

    /**
     * 环绕通知
     * proceedingJoinPoint对象是JoinPoint的子接口，只在around切面方法中，添加了两个方法
     * Object proceed() throws Throwable -------执行目标方法
     * Object proceed(Object[] var1) throws Throwable------传入的新参数去执行目标方法
     */
    @Around(value = "pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String methodName = proceedingJoinPoint.getSignature().getName();
        System.out.println("方法:"+methodName+"的环绕通知开始执行");

        long startTime = System.currentTimeMillis();

        Object proceed = proceedingJoinPoint.proceed();

        long endTime = System.currentTimeMillis();
        System.out.println("方法"+methodName+"耗时:"+(endTime-startTime)+"毫秒");
        return proceed;
    }

}
