package com.jt.test.helper;

import java.util.concurrent.CountDownLatch;

/**
 * WaitThread
 *
 * @author jt
 * @date 2022/5/10
 **/
public class WaitThread extends Thread{
    private String name;
    private CountDownLatch count;

    public WaitThread(String name, CountDownLatch count) {
        this.name = name;
        this.count = count;
    }

    @Override
    public void run(){
        try {
            System.out.println("线程："+name+"wait..");
            count.await();
            System.out.println(Thread.currentThread().getName());
            System.out.println("线程"+name+"continue running...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
