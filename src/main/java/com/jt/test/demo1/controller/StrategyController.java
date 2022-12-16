package com.jt.test.demo1.controller;

import com.jt.test.demo1.helper.LoginHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * StrategyController
 * 策略模式测试
 * @Author: jt
 * @Date: 2022/12/16 9:26
 */
@Api(tags = "策略模式")
@RequestMapping("/strategy")
@RestController
public class StrategyController {

    @Autowired
    private LoginHelper loginHelper;

    @ApiOperation("策略模式登录")
    @GetMapping("/login")
    public String login(String type,String username,String password){
        return loginHelper.login(type, username, password);
    }

}
