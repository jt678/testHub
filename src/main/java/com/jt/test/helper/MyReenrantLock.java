package com.jt.test.helper;

import com.jt.test.junitTest.HumanClassTest;

import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * MyReenrantLock
 *
 * @author jt
 * @date 2022/5/12
 **/

public class MyReenrantLock implements Runnable{

    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        for (int i = 0;i<10;i++) {
            //上锁,后面必须接try finally
            lock.lock();
            try {
                final int max_test = HumanClassTest.max_test;
                System.out.println("当前线程名："+Thread.currentThread().getName()+",i="+i+"now:"+System.currentTimeMillis());
            } finally {
                //解锁，必须在finally第一行
                lock.unlock();
            }
        }

    }
}

