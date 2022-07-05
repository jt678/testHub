package com.jt.test.helper;

import com.jt.test.utils.StringUtils;
import me.chanjar.weixin.common.util.crypto.SHA1;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

/**
 * WeChatHelper
 *
 * @Author: jt
 * @Date: 2022/7/4 16:05
 */
@Service
public class WeChatHelper {
    @Value("${wechat.mpAppId}")
    private String appId;
    @Value("${wechat.mpAppSecret}")
    private String appSecret;
    @Value("${wechat.mpToken}")
    private String token;

    /**
     * token 验证
     */
    public void token(HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.setCharacterEncoding("UTF-8");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        //这个东西要返回给微信服务端，微信服务端来判断接口是否连接上了
        String echostr = request.getParameter("echostr");
        PrintWriter out = null;

        try {
            out = response.getWriter();
            String shaGen = SHA1.gen(timestamp, nonce, token);
            if (signature.equals(shaGen)){
                out.write(echostr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

}


