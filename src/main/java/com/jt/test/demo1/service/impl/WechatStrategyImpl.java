package com.jt.test.demo1.service.impl;

import com.jt.test.demo1.service.LoginStrategy;
import org.springframework.stereotype.Service;

/**
 * WechatStrategyImpl
 *
 * @Author: jt
 * @Date: 2022/12/16 10:10
 */
@Service
public class WechatStrategyImpl implements LoginStrategy {
    @Override
    public String login(String username, String password) {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "微信登录成功";
    }

    @Override
    public String getType() {
        return "wechat";
    }
}
