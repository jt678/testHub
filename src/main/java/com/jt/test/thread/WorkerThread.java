package com.jt.test.thread;

import java.util.concurrent.CountDownLatch;

/**
 * WorkerThread
 *
 * @author jt
 * @date 2022/5/10
 **/
public class WorkerThread extends Thread{
    private String name;
    private CountDownLatch countDownLatch;

    public WorkerThread(String name, CountDownLatch countDownLatch) {
        this.name = name;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run(){
        System.out.println(name + "begin");
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name+"is end");
        countDownLatch.countDown();
    }
}
