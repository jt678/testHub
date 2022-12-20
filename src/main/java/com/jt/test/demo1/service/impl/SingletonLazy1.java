package com.jt.test.demo1.service.impl;

/**
 * SingletonLazy1
 * 懒汉实现1--线程不安全
 * @Author: jt
 * @Date: 2022/12/9 11:17
 */
public class SingletonLazy1 {
    private static SingletonLazy1 singletonLazy;

    //将构造器设置为private，禁止通过new实例化，而是在类加载时就产生实例
    private SingletonLazy1(){

    }

    public static SingletonLazy1 getInstance(){
        if (null== singletonLazy){
            //开始两个线程同时到达，在这里判断都为null，所以产生了两个实例，后面的线程正常，是因为检测到实例已经创建======线程不安全
            singletonLazy = new SingletonLazy1();
        }

        return singletonLazy;
    }
}
