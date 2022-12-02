package com.jt.test.demo1.junitTest;

import com.jt.test.TestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * SynchronizedTest
 *
 * @author jt
 * @date 2022/5/12
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class SynchronizedTest {
    @Test
    public void SynchronizedTest() throws Exception{
        new Thread(()->{
            SynLock.task1();
        }).start();

        new Thread(()->{
            SynLock.task2();
        }).start();
    }
}

class SynLock{
    public synchronized static void task1(){
        System.out.println("当前线程:"+Thread.currentThread().getName()+"-----开始");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("当前线程:"+Thread.currentThread().getName()+"-----结束");
    }

    public synchronized static void task2(){
        System.out.println("当前线程:"+Thread.currentThread().getName()+"-----开始");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("当前线程:"+Thread.currentThread().getName()+"-----结束");
    }

}
