package com.jt.test.demo1.helper;

import com.jt.test.demo1.factory.LoginStrategyFactory1;
import com.jt.test.demo1.factory.LoginStrategyFactory2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * LoginHelper
 *
 * @Author: jt
 * @Date: 2022/12/16 9:24
 */
@Service
public class LoginHelper {

    @Autowired
    private LoginStrategyFactory2 loginStrategyFactory1;

    public String login(String type,String username,String password){
        //业务处理
        System.out.println("登录处理");

        return loginStrategyFactory1.login(type,username,password);
    }
}
