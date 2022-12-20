package com.jt.test.demo1.service.impl;

/**
 * SingletonLazy2
 * 懒汉实现2---线程安全，效率低，整个方法被加锁
 * @Author: jt
 * @Date: 2022/12/9 11:32
 */
public class SingletonLazy2 {
    private static SingletonLazy2 singletonLazy;

    private SingletonLazy2() {

    }
    //锁了方法，整个方法被加锁，只能等待上一个线程完成之后才能执行该方法，实现了线程安全的单例，但是效率太低
    public static synchronized SingletonLazy2 getInstance() {
//        try {
            if (null == singletonLazy) {
                // 模拟在创建对象之前做一些准备工作，这里为什么不sleep，因为sleep1秒，控制台上不会输出任何内容
                // 但是debug时给足时间的话又会正常显示，怀疑是junit的test，按照顺序执行完程序就结束了，不会继续运行，代码都在休眠还没来的及sout程序就结束了
//                Thread.sleep(1000);
                singletonLazy = new SingletonLazy2();
            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return singletonLazy;
    }

}
