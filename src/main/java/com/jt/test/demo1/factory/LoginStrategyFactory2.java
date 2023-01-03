package com.jt.test.demo1.factory;

import com.jt.test.demo1.service.LoginStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * LoginStrategyFactory2
 * 登录策略工厂2,利用@autowired注解，使代码更加简便
 * @Author: jt
 * @Date: 2022/12/21 9:30
 */
@Component
public class LoginStrategyFactory2 {
    @Autowired
    private final Map<String, LoginStrategy> map = new HashMap<>();

    public String login(String type, String username, String password) {
        String relType = null;

        switch (type.toLowerCase()) {
            case "qq":
                relType = "qqStrategyImpl";
                break;
            case "wechat":
                relType = "wechatStrategyImpl";
                break;
            default: return "错误的登录类型,登录失败";
        }

        LoginStrategy loginStrategy = map.get(relType);
        return loginStrategy.login(username,password);
    }
}
