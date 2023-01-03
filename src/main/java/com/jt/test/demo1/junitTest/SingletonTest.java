package com.jt.test.demo1.junitTest;

import com.jt.test.demo1.service.impl.SingletonHungary;
import com.jt.test.demo1.service.impl.SingletonLazy1;
import com.jt.test.demo1.service.impl.SingletonLazy2;
import com.jt.test.demo1.service.impl.SingletonLazy3;
import org.junit.Test;

/**
 * SingletonTest
 * 单例模式测试
 *
 * @Author: jt
 * @Date: 2022/12/9 10:10
 */
public class SingletonTest {
    //模拟10个异步线程去调取方法，看是否得到的时单例
    @Test
    public void SingletonLazyTest() {
        TestThread[] testThreadArr = new TestThread[10];
        for (int i = 0; i < testThreadArr.length; i++) {
            testThreadArr[i] = new TestThread();
            testThreadArr[i].start();
        }
    }
}
//测试线程
class TestThread extends Thread {
    @Override
    public void run() {
        SingletonLazy1 instance = SingletonLazy1.getInstance();

        if (instance==null){
            System.out.println("未获取到实例");
        }
        System.out.println("线程："+TestThread.currentThread().getName()+"hash:"+ instance.hashCode());
    }
}
