package com.jt.test.demo1.common;

/**
 * SingletonLazy3
 * 懒汉实现5---使用DCL双检查机制（推荐）
 * 解决效率和线程不安全问题
 * @Author: jt
 * @Date: 2022/12/9 13:46
 */
public class SingletonLazy3 {
    private static SingletonLazy3 singletonLazy;

    private SingletonLazy3(){

    }

    public static SingletonLazy3 getInstance(){
        //判断是否没有创建过实例，没有创建过继续走，创建过直接返回已创建实例
        if (null == singletonLazy){              //代码1
            //这一步加锁是为了防止线程同时走到这里创建两个实例
            synchronized (SingletonLazy3.class){
                //这里再判断一次是防止出现这种情况（加强线程安全）：
                // ----线程A，B同时走到代码1的判断，他两都为空所以可以继续往下走，但是线程A先拿到锁，创建了实例，线程B再拿到锁（因为他也是通过第一层判断的），再创建一个实例
                if (null == singletonLazy){
                    singletonLazy = new SingletonLazy3();
                }
            }
        }
        return singletonLazy;
    }
}
