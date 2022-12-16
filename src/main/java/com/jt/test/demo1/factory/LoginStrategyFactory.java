package com.jt.test.demo1.factory;

import com.jt.test.demo1.service.LoginStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * LoginStrategyFactory
 *
 * @Author: jt
 * @Date: 2022/12/15 16:36
 */
@SpringBootApplication
public class LoginStrategyFactory implements CommandLineRunner {

    /**
     * 用来存放不同策略的数组
     */
    private final List<LoginStrategy> strategies = new ArrayList<>();

    @Autowired
    private ApplicationContext applicationContext;

    public String login(String type, String username, String password) {
        for (LoginStrategy strategy : strategies) {
            if (type.equals(strategy.getType())) {
                return strategy.login(username, password);
            }
        }
        return "错误的登录类型,登录失败";
    }

    public List<String> getTypes(){
        List<String> list = new ArrayList<>();
        for (LoginStrategy strategy : strategies) {
            list.add(strategy.getType());
        }
        return list;
    }

    @Override
    public void run(String... args) throws Exception {
        String[] beans = applicationContext.getBeanDefinitionNames();
        // 将所有登录策略组装进策略列表中
        for (String bean : beans) {
            Object beanObj = applicationContext.getBean(bean);
            if (beanObj instanceof LoginStrategy){
                strategies.add((LoginStrategy)beanObj);
            }
        }
    }
}
