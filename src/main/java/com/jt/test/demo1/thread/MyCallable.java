package com.jt.test.demo1.thread;

import java.util.concurrent.Callable;

/**
 * MyCallable
 * 配合callableTask测试创建的一个callableTask
 * @Author: jt
 * @Date: 2023/5/15 14:16
 */
public class MyCallable implements Callable<String> {
    private Integer num;

    public MyCallable(Integer num) {
        this.num = num;
    }

    @Override
    public String call() throws Exception {
        //测试是否按顺序输出,如果用ExecutorCompletionService则先执行完的会先获取到
        //模拟不同线程处理业务时间不同
        if (num==1){
            Thread.sleep(2000);
        }
        if(num==3){
            Thread.sleep(1000);
        }

        return String.valueOf(this.num);
    }
}
