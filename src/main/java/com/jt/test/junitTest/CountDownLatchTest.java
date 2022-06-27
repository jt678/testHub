package com.jt.test.junitTest;

import com.jt.test.thread.WaitThread;
import com.jt.test.thread.WorkerThread;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch
 *
 * @author jt
 * @date 2022/5/10
 **/
public class CountDownLatchTest {
    public static void main(String[] args) {
        CountDownLatch cdl = new CountDownLatch(3);

        WaitThread waitThread1 = new WaitThread("wait-1",cdl);
        WaitThread waitThread2 = new WaitThread("wait-2",cdl);

        WorkerThread workerThread1 = new WorkerThread("work-1",cdl);
        WorkerThread workerThread2 = new WorkerThread("work-2",cdl);
        WorkerThread workerThread3 = new WorkerThread("work-3",cdl);

        waitThread1.start();

        waitThread2.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        workerThread1.start();
        workerThread2.start();
        workerThread3.start();

    }

}
