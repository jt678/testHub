package com.jt.test.controller;

import com.jt.test.helper.WeChatHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * WeChatController
 *
 * @Author: jt
 * @Date: 2022/7/4 13:59
 */
@Slf4j
@Controller
@Api(tags = "微信公众号开发")
public class WeChatController {
    @Autowired
    private WeChatHelper weChatHelper;

    @ApiOperation("对请求进行校验")
    @RequestMapping(value = "/wx")
    @ResponseBody
    public void test(HttpServletRequest request, HttpServletResponse response) throws Exception {
        weChatHelper.token(request,response);
    }

}
