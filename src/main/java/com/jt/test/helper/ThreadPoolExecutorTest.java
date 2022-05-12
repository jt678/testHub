package com.jt.test.helper;

import com.jt.test.TestApplicationMapTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * ThreadPoolExecutorTest
 *
 * @author jt
 * @date 2022/4/2
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationMapTest.class)
public class ThreadPoolExecutorTest {
    @Autowired
    TaskThreadHelper taskThreadHelper;

    @Resource(name = "threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 单线程执行
     */
    @Test
    public void ThreadPoolExec(){
        long start = System.currentTimeMillis();

        List iList = new ArrayList<>();
        List list1 = taskThreadHelper.task1();
        List list2 = taskThreadHelper.task2();
        iList.addAll(list1);
        iList.addAll(list2);

        long end = System.currentTimeMillis();

        System.out.println("耗时"+(end-start)+"数组里有"+iList.size()+"条记录");
    }

    /**
     * 开启两个线程执行
     */
    @Test
    public void TwoThreadPoolExec() {
        List iList = new ArrayList<>();

        long start = System.currentTimeMillis();
        //开启线程1
        threadPoolTaskExecutor.execute(()->{
            List list1 = taskThreadHelper.task1();
            iList.addAll(list1);
        });

        //开启线程2
        threadPoolTaskExecutor.execute(()->{
            List list2 = taskThreadHelper.task2();
            iList.addAll(list2);
        });

        long end = System.currentTimeMillis();

        System.out.println("耗时"+(end-start)+"数组里有"+iList.size()+"条记录");
    }

}
