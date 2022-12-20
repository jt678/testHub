//package com.jt.test.demo1.helper;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * SingletonHelper
// * 这里本来是结合单例模式和策略模式，但是没有成功，怀疑是因为@component这些注解已经实现了单例，导致几种实现方式都能实现单例，并没有达到之前要的效果，故放弃此代码
// * 保留的原因是因为这里有用到向线程中传参的方法，以后可以用到。
// * @Author: jt
// * @Date: 2022/12/16 13:38
// */
//@Service
//public class SingletonHelper {
//    @Autowired
//    private SingletonStrategyFactory singletonStrategyFactory;
//
//    public void getInstance(String type) {
//        TestThread2[] testThreadArr = new TestThread2[10];
//        //这种方式可以在线程中带入参数！！！！，之前直接重写线程的run方法不能带入参数，要在创建线程时new runable再重写run方法（可简化成()->{}）
//        for (int i = 0; i < testThreadArr.length; i++) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    int hashCode = singletonStrategyFactory.getInstance(type).hashCode();
//                    System.out.println("线程：" + TestThread2.currentThread().getName() + "hash:" + hashCode);
//                }
//            }).start();
//        }
//    }
//}
//
//class TestThread2 extends Thread {
//
//}