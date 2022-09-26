package com.jt.test.controller;

import com.jt.test.common.HttpResult;
import com.jt.test.domain.vo.UserVO;
import com.jt.test.helper.WeChatHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * WeChatController
 *
 * @Author: jt
 * @Date: 2022/7/4 13:59
 */
@Slf4j
@Controller
@Api(tags = "微信公众号开发")
//@RequestMapping("/wx")
public class WeChatController {
    @Autowired
    private WeChatHelper weChatHelper;

    @ApiOperation("对请求进行校验+自动回复")
    @GetMapping(value = "/wx")
    @ResponseBody
    public void test(HttpServletRequest request, HttpServletResponse response) throws Exception {
        weChatHelper.token(request,response);
    }

    @ApiOperation("获取关注用户基本信息+入库")
    @GetMapping(value = "/getBaseInfo")
    @ResponseBody
    public HttpResult<List<UserVO>> getBaseInfo(){

        return HttpResult.success(weChatHelper.getBaseInfo());

    }

    @ApiOperation("根据openId+筛选人群发信息")
    @PostMapping("/groupSending")
    @ResponseBody
    public void groupSending(){
        String s = weChatHelper.groupSending();
        System.out.println(s);
    }
}
