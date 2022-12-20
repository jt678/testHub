package com.jt.test.demo1.service.impl;

import com.jt.test.demo1.service.LoginStrategy;
import org.springframework.stereotype.Service;

/**
 * QQStrategyImpl
 *
 * @Author: jt
 * @Date: 2022/12/15 16:43
 */
@Service
public class QqStrategyImpl implements LoginStrategy {
    @Override
    public String login(String username, String password) {

        try {
            //模拟登录处理逻辑花费时间
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "通过QQ登录成功";
    }

    @Override
    public String getType() {
        return "QQ";
    }
}
