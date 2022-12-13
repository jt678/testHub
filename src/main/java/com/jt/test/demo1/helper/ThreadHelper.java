package com.jt.test.demo1.helper;

import com.alibaba.nacos.shaded.org.checkerframework.checker.units.qual.A;
import com.jt.test.demo1.domain.entity.Order;
import com.jt.test.demo1.service.OrderService;
import com.jt.test.demo1.service.RedisService;
import com.jt.test.demo1.utils.ThreadPool.MyThreadTask;
import com.jt.test.demo1.utils.ThreadPool.ThreadTaskUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * ThreadHelper
 *
 * @author jt
 * @date 2022/4/26
 **/
@Service
public class ThreadHelper {

    private final Logger myLog = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private OrderService service;

    @Resource(name = "threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    private RedisService redisService;

    public void threadTime() {
        Date startTime = new Date();
        threadPoolTaskExecutor.execute(() -> {
            service.list();
            Date endTime = startTime;
        });



        Date endTime = new Date();
        Long finalTime = endTime.getTime()-startTime.getTime();
        System.out.println("cost ：" +finalTime);

    }

    @Async("testThread")
    public void threadTime1() throws InterruptedException {

        myLog.info("方法1开始");
        String name = Thread.currentThread().getName();
        System.out.println(name);
        service.list();
        System.out.println("方法1执行方法中");
        myLog.info("方法1结束");
    }
    @Async("testThread")
    public void threadTime2() throws InterruptedException {

        myLog.info("方法2开始");
        String name = Thread.currentThread().getName();
        System.out.println(name);
        service.list();
        System.out.println("方法2执行方法中");
        myLog.info("方法2结束");
    }

    @Async("testThread")
    public void threadTime3() throws InterruptedException {

        myLog.info("方法3开始");
        String name = Thread.currentThread().getName();
        System.out.println(name);
        service.list();
        System.out.println("方法3执行方法中");
        myLog.info("方法3结束");
    }

    public List<Order> threadTime4() throws InterruptedException {


        myLog.info("方法4开始");
        String name = Thread.currentThread().getName();
        System.out.println(name);

        List<Order> OrderList = service.list();
        System.out.println("方法4执行方法中");
        myLog.info("方法4结束");
        return  OrderList;
    }

    public List<Order> threadTime5() {
        myLog.info("方法5开始");
        String name = Thread.currentThread().getName();
        System.out.println(name);

        List<Order> OrderList = service.list();
        System.out.println("方法5执行方法中");
        myLog.info("方法5结束");
        return  OrderList;
    }

    /**
     * 测试线程池（Configuration+bean+Async注解实现）
     */
    @Async("testThread")
    public void hello(){
        //springboot启动源码里面的启动计时方法
        try {
            String threadName = Thread.currentThread().getName();
            myLog.info("开始执行，执行线程名称："+ threadName);
            long startTime = System.nanoTime();
            Thread.sleep(5000);
            Duration timeTakenToStartup = Duration.ofNanos(System.nanoTime() - startTime);
            // 存redis，检测是否实现单例
            int hash = redisService.hashCode();
            redisService.set(threadName,hash);
            myLog.info(threadName +"执行完成时间："+timeTakenToStartup);
        } catch (InterruptedException e) {
            e.printStackTrace();
            myLog.error("出现线程错误");
        }
    }

    /**
     * 测试线程池（单例获取）
     */
    @Async
    public void hello2() {
            try {
                System.out.println("========请求开始========");
                //模拟请求中的数据
                String requestData = "这是请求中要处理的数据";
                //交给线程池异步处理--------实际上是同步处理
                String re = ThreadTaskUtil.execute(new MyThreadTask(requestData));
                System.out.println("处理成功,返回结果="+re);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                myLog.error("单例线程获取出错");
            }
    }
}
