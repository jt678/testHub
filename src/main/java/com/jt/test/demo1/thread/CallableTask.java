package com.jt.test.demo1.thread;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * CallableTask
 * 关于线程池callable任务的一些测试demo
 *
 * @Author: jt
 * @Date: 2023/5/15 9:37
 */
public class CallableTask implements Callable<String> {
    public CallableTask(String acceptStr) {
        this.acceptStr = acceptStr;
    }

    private String acceptStr;

    @Override
    public String call() throws Exception {
        // 任务阻塞 1 秒
        Thread.sleep(1000);
        return this.acceptStr + " append some chars and return it!";
    }

    /**
     * Callable工作的demo
     * @param args
     */
//    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        CallableTask callableTask = new CallableTask("this is my callableTask!");
//        FutureTask<String> futureTask = new FutureTask<>(callableTask);
//        long startTime = System.nanoTime();
//        //创建一个线程去执行这个FutureTask-->里面放的其实是我们的callable任务
////        new Thread(futureTask).start();
//
//        // 调用FutureTask的get方法获取返回执行结果，如果不调用get方法，则主线程不会阻塞，会直接去执行下面的代码，而不是等future线程执行完之后再继续执行
//        String result = futureTask.get();
//
//        long endTime = System.nanoTime();
//
//        Duration costTime = Duration.ofNanos(endTime - startTime);
//
//        System.out.println("花费时间:"+costTime);
//        System.out.println("执行结果:" + result);
//    }

    /**
     * 使用ExecutorCompletionService的demo
     * ExecutorCompletionService维护了一个QueueingFuture（队列任务）当通过ExecutorCompletionService提交的任务执行完成后，将结果放入QueueingFuture中
     * 然后通过take和poll方法获取执行结果时会阻塞线程，直到当QueueingFuture中有结果时就会立即返回
     * 这样能够保证先执行完的任务能先被拿到，而不用按顺序等待，例如5在1之前就执行完了，1是最后才执行完成，但是如果按顺序要等到最后的1执行完才能获取到5的值，其实5早就以及执行完成
     *
     * 因为MyCallable重写了run方法，最后两位必是3、1
     * 运行时间=====2s（因为执行最长的任务只有2s）
     * @param args
     */
//    public static void main(String[] args) {
//        System.out.println("starting...");
//        long startTime = System.nanoTime();
//
//        int taskSize = 5;
//        ExecutorService pool = Executors.newFixedThreadPool(taskSize);
//        ExecutorCompletionService<String> completionService = new ExecutorCompletionService<>(pool);
//        try {
//            for (int i = 1; i <= taskSize; i++) {
//                MyCallable myCallable = new MyCallable(i);
//
//                completionService.submit(myCallable);
//            }
//            // 关闭线程池
//            pool.shutdown();
//            for (int i = 1; i <= taskSize; i++) {
//                //此处阻塞了线程，等能get到值的时候再继续执行下一个for
//                System.out.println(completionService.take().get());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        long endTime = System.nanoTime();
//        Duration costTime = Duration.ofNanos(endTime - startTime);
//        System.out.println("end...cost time = " + costTime);
//    }

    /**
     * 用一个list去输出执行结果测试
     *  结果：按顺序打印，最后两外就算重写了run方法还是4、5
     *  运行时间======2s（这个地方为什么也是2s？？？预期是3s）
     */
    public static void main(String[] args) {
        System.out.println("starting...");
        long startTime = System.nanoTime();
        int taskSize = 5;
        // 创建一个线程池
        ExecutorService pool = Executors.newFixedThreadPool(taskSize);
        try {
            // 创建多个返回值的任务
            List<Future> futureList = new ArrayList<>();
            for (int i = 1; i <= taskSize; i++) {
                MyCallable callable = new MyCallable(i);
                Future<String> future = pool.submit(callable);
                futureList.add(future);
                System.out.println("已添加" + i);
            }
            for (Future future : futureList) {
                System.out.println(future.get().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //关闭线程池
        pool.shutdown();
        long endTime = System.nanoTime();
        Duration costTime = Duration.ofNanos(endTime - startTime);
        System.out.println("end...cost time = " + costTime);
    }
}
