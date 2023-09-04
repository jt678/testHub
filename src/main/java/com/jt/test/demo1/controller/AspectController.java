package com.jt.test.demo1.controller;

import com.jt.test.demo1.annotation.MyAnnotation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * AspectController
 *
 * @Author: jt
 * @Date: 2023/7/11 9:55
 */
@RestController
@RequestMapping("/aspect")
@Api(tags = "切面测试controller")
public class AspectController {

    @ApiOperation("执行测试")
    @MyAnnotation
    @GetMapping("/doSome")
    public String doSome(Integer modeName) throws Exception {
        if (modeName == 0) {
            return "modeName is " + modeName;
        }else {
            throw new IOException("捕捉非0异常");
        }
    }
}
