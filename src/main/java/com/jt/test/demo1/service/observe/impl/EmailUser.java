package com.jt.test.demo1.service.observe.impl;

import com.jt.test.demo1.service.observe.EmailObserver;
import lombok.AllArgsConstructor;

/**
 * EmailUser
 * email观察者实现
 * @Author: jt
 * @Date: 2023/9/5 14:13
 */
@AllArgsConstructor
public class EmailUser implements EmailObserver {
    //观察者的一些属性
    private Long id;
    private String name;

    @Override
    public void onNewEmailReceived(String emailContent) {
        System.out.println("用户"+name+"收到了邮件,内容如下:\n"+emailContent);
    }
}
