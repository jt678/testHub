package com.jt.test.demo1.service.impl;

/**
 * SingletonHungary
 * 饿汉模式
 * @Author: jt
 * @Date: 2022/12/9 10:11
 */

import lombok.extern.slf4j.Slf4j;

/**
 * 饿汉模式，在调用方法之前就产生了实例，占用资源
 * 适合单例类很小的，在初始化时就会用到的类
 */
@Slf4j
public class SingletonHungary {
    private static SingletonHungary singletonHungary = new SingletonHungary();
    //将构造器设置为private，禁止通过new实例化，而是在类加载时就产生实例
    private SingletonHungary(){

    }

    //这里Junit测试时没有东西打印
    //猜测是因为静态变量在程序开启时会加载，在这时候如上代码new 了一个SingletonHungary，后面在调用的时候就直接返回这个值了,但是test并没有启动整个程序
    //启动后调用接口显示正常,hashcode相同

    public static SingletonHungary getInstance(){

        return singletonHungary;
    }
}
