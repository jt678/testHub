package com.jt.test.controller;

import com.alibaba.druid.support.http.util.IPAddress;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.jt.test.common.HttpResult;
import com.jt.test.helper.IpAddressHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * IpAddressController
 *
 * @Author: jt
 * @Date: 2022/6/15 16:23
 */
@Api(tags = "获取ip地址和主机名接口")
@ApiSupport(author = "jt")
@RestController
@RequestMapping("/machine")
public class IpAddressController {

    @Autowired
    private IpAddressHelper ipAddressHelper;

    @GetMapping("/ip")
    @ApiOperation("获取ip")
    public HttpResult<String> getIp(){


        return  HttpResult.success(ipAddressHelper.getIp());
    }


    @GetMapping("/name")
    @ApiOperation("获取主机名")
    public HttpResult<String>  getName() throws SocketException {


        return  HttpResult.success(ipAddressHelper.getName());
    }
}
