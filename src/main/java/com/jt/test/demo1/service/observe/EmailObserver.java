package com.jt.test.demo1.service.observe;

/**
 * EmailObserver
 * 定义观察者接口
 * @Author: jt
 * @Date: 2023/9/5 14:11
 */
public interface EmailObserver {
    /**
     * 提供新消息产生时的回调消费方法
     * @param emailContent
     */
    void onNewEmailReceived(String emailContent);
}
