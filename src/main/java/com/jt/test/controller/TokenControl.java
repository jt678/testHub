package com.jt.test.controller;
import com.jt.test.utils.PushControllerUtils.SignUtil;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@RestController
@RequestMapping("/weixin")
@Api(tags ="网上找到一套用于群发的测试的token验证入口")
public class TokenControl {
    /**
     * 微信消息接收和token验证
     */
    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public String get(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("----------------验证微信服务号信息开始----------");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            System.out.println("----验证服务号结束.........");
            return echostr;
        }else{
            return null;
        }
    }//public
}