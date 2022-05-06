package com.jt.test.helper;

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

public class ThreadPoolExecutorTest {
    public static void main(String[] args) {
        //线程池的七个参数详情
       ThreadPoolExecutor executor = new ThreadPoolExecutor(
               //主要线程数（最少）
               5,
               //最大线程池
               10,
               //保持活跃时间，空闲超过15（单位）自动休眠
               15,
               //时间单位
               TimeUnit.SECONDS,

               new ArrayBlockingQueue<>(3),
               new ThreadPoolExecutor.CallerRunsPolicy()
       );

        long start = System.currentTimeMillis();

        int jobs = 1000000;
        List iList = new LinkedList();
        for (int i = 0 ;i< jobs ;i++) {

            iList.add(i);
        }

        String a = 123 + "";
        int job2 = 100000;
        for (int i = 0;i<job2;i++){
            iList.add(i);
        }
        long end = System.currentTimeMillis();

        System.out.println("for循环花费时间"+(end-start)+"数组里有"+iList.size()+"条记录"+a);
    }
}
