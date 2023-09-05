package com.jt.test.demo1.service.observe;

import java.util.List;

/**
 * EmailSubject
 * 定义被观察者接口
 * @Author: jt
 * @Date: 2023/9/5 14:12
 */
public interface EmailSubject {

    /**
     * 注册观察者
     * @param observer
     */
    void registerObserver(EmailObserver observer);

    /**
     * 移除观察者
     * @param observer
     */
    void removeObserver(EmailObserver observer);

    /**
     * 批量注册观察者
     */
    void registerObservers(List<EmailObserver> observerList);

    /**
     * 提醒观察者
     * @param emailContent
     */
    void notifyObservers(String emailContent);
}
