package com.jt.test.demo1.domain;

/**
 * Handler
 * 处理者抽象类，具体实现由他的不通子类来实现，参考Animal抽象类
 * @Author: jt
 * @Date: 2023/2/8 16:46
 */
abstract class Handler {
    private Handler next;

    public Handler getNext() {
        return next;
    }

    public void setNext(Handler next) {
        this.next = next;
    }

    //处理请求的方法
    public abstract void handleRequest(String request);
}
