package com.jt.test.controller;

import com.jt.test.common.HttpResult;
import com.jt.test.domain.bo.SendBO;
import com.jt.test.helper.MailHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * MailController
 *
 * @Author: jt
 * @Date: 2022/8/8 11:46
 */
@Api(tags = "邮件功能")
@RestController
public class MailController {

    @Autowired
    private MailHelper mailHelper;

    @GetMapping("/send")
    @ApiOperation("发邮件功能")
    public HttpResult send() {
        return mailHelper.send();
    }

    @PostMapping("sendBatch")
    @ApiOperation("批量发送邮件")
    public HttpResult sendBatch(SendBO sendBO) {
        return mailHelper.sendBatch(sendBO);
    }

}
