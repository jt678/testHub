package com.jt.test.junitTest;

import com.jt.test.thread.WaitThread;
import com.jt.test.thread.WorkerThread;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch
 *
 * @author jt
 * @date 2022/5/10
 **/
public class CountDownLatchTest {
    /**
     * countDownLatch（倒计时锁存器）
     * 应用场景：
     * 1.某个线程需要在其他n个线程执行完之后再向下执行
     * 2.多个线程并行执行同一个任务，提高响应速度
     */
    @Test
    public void test() {
        //后面的参数--3代表线程个数，每当一个任务线程执行完毕，就将计数器-1，直到为0时才能够继续执行被锁的代码
        CountDownLatch cdl = new CountDownLatch(3);

        WaitThread waitThread1 = new WaitThread("wait-1", cdl);
        WaitThread waitThread2 = new WaitThread("wait-2", cdl);

        WorkerThread workerThread1 = new WorkerThread("work-1", cdl);
        WorkerThread workerThread2 = new WorkerThread("work-2", cdl);
        WorkerThread workerThread3 = new WorkerThread("work-3", cdl);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        workerThread1.start();
        workerThread2.start();
        workerThread3.start();

        waitThread1.start();

        waitThread2.start();
    }


    /**
     * 跑步考试例子
     */
    @Test
    public void exam(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println("考试开始"+sdf.format(new Date()));

        CountDownLatch countDownLatch = new CountDownLatch(2);

        new Thread(()->{
            //跑十圈
            int j=0;
            for (int i = 0; i < 10; i++) {
                j++;
            }
            System.out.println("第一个人到达，count=1，跑了"+j+"圈");
            try {
                Thread.sleep(10000);
                System.out.println("考生1跑完休息了10秒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //唤醒锁存器，告诉他完成一次任务了
            countDownLatch.countDown();
        }
        ).start();

        new Thread(()->{
            int j=0;
            for (int i = 0; i < 10; i++) {
                j++;
            }
            System.out.println("第二个人到达,count=0，跑了"+j+"圈");
            try {
                Thread.sleep(10000);
                System.out.println("考生2跑完休息了10秒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //唤醒锁存器，告诉他完成一次任务了
            countDownLatch.countDown();
        }).start();

        try {
            //阻塞线程，在count不为0之前不执行代码段，正常执行的时候在第一个考生跑完就可能考试结束了，休息也没有执行
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("所有考生到达,跑步结束");


    }

}
