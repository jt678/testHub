package com.jt.test.junitTest;

import com.jt.test.TestApplicationMapTest;
import com.jt.test.helper.MyReenrantLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



/**
 * LockTest
 *
 * @author jt
 * @date 2022/5/12
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationMapTest.class)
public class ReenrantLockTest {

    @Test
    public void lockTest() throws InterruptedException {
        MyReenrantLock myLock = new MyReenrantLock();
        Thread thread1 = new Thread(myLock);
        Thread thread2 = new Thread(myLock);
        Thread thread3 = new Thread(myLock);

        thread1.start();
        thread2.start();
        thread3.start();

    }
}
