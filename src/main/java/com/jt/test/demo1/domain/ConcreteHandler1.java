package com.jt.test.demo1.domain;

/**
 * ConcreteHandler1
 * 具体处理者1实例
 * @Author: jt
 * @Date: 2023/2/8 16:49
 */
public class ConcreteHandler1 extends Handler {
    @Override
    public void handleRequest(String request) {
        //如果请求为one则直接处理该请求
        if("one".equals(request))  {
            System.out.println("具体处理者1负责处理该请求！");
        }else {
            //拿到客户端设置的next handler，如果不为空交给这个指定的handler去处理，此处handler1就不用去管了
            if(getNext()!=null)  {
                getNext().handleRequest(request);
            }
            else {
                System.out.println("处理者1:没有人处理该请求！");
            }
        }
    }
}
