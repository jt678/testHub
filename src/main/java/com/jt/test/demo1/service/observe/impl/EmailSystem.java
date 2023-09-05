package com.jt.test.demo1.service.observe.impl;

import com.jt.test.demo1.service.observe.EmailObserver;
import com.jt.test.demo1.service.observe.EmailSubject;

import java.util.ArrayList;
import java.util.List;

/**
 * EmailSystem
 * email被观察者实现
 * @Author: jt
 * @Date: 2023/9/5 14:14
 */
public class EmailSystem implements EmailSubject {

    private List<EmailObserver> observers = new ArrayList<>();
    @Override
    public void registerObserver(EmailObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(EmailObserver observer) {
        observers.remove(observer);

    }

    @Override
    public void registerObservers(List<EmailObserver> observerList) {
        observers.addAll(observerList);
    }

    @Override
    public void notifyObservers(String emailContent) {
        for (EmailObserver observer : observers) {
            //有新邮件时触发该方法，向订阅者（观察者）推送新邮件内容
            observer.onNewEmailReceived(emailContent);
        }
    }
}
