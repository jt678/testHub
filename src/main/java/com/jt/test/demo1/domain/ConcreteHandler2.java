package com.jt.test.demo1.domain;

/**
 * ConcreteHandler2
 * 具体处理者2实例
 * @Author: jt
 * @Date: 2023/2/8 16:53
 */
public class ConcreteHandler2 extends Handler{
    @Override
    public void handleRequest(String request) {
        if ("two".equals(request)){
            System.out.println("具体处理者2负责处理该请求！");
        }else {
            if (getNext()!=null){
                getNext().handleRequest(request);
            }else {
                System.out.println("处理者2:没有人处理该请求！");
            }
        }
    }
}
