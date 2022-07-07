package com.jt.test.helper;

import com.jt.test.domain.vo.UserVO;
import com.jt.test.utils.StringUtils;
import com.jt.test.utils.wxMessageUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.util.crypto.SHA1;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * WeChatHelper
 *
 * @Author: jt
 * @Date: 2022/7/4 16:05
 */
@Service
@Slf4j
public class WeChatHelper {
    @Value("${wechat.mpAppId}")
    private String appId;
    @Value("${wechat.mpAppSecret}")
    private String appSecret;
    @Value("${wechat.mpToken}")
    private String token;

    /**
     * token 验证 + 被动回复功能
     */
    public void token(HttpServletRequest request, HttpServletResponse response) throws Exception {


        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        //这个东西要返回给微信服务端，微信服务端来判断接口是否连接上了
        String echostr = request.getParameter("echostr");
        String method = request.getMethod();

        String requestURI = request.getRequestURI();
        //如果是get请求，说明是配置在微信后台url发过来的请求
        if (method.equals("GET")) {
            PrintWriter out = null;
            out = response.getWriter();
            String shaGen = SHA1.gen(timestamp, nonce, token);
            if (signature.equals(shaGen)) {
                try {
                    log.info("签名校验成功！");
                    out.write(echostr);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    out.close();
                }
            } else {
                log.info("签名校验失败！");
            }
        } else {
            //如果不是get请求，是POST请求说明是微信公众号里面来的内容
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            Map<String, String> map = wxMessageUtils.getClientMsg(request);
            System.out.println("开始构造消息");
            String result = "";
            result = wxMessageUtils.buildXml(map);

            if (result.equals("")) {
                result = "未正确响应";
            }
            try {
                response.getWriter().write(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public List<UserVO> getBaseInfo() {
        String appId1 = appId;
        String appSecret1 = appSecret;
        String accessToken = wxMessageUtils.getAccessToken(appId,appSecret);
        List<UserVO> userInfoList = wxMessageUtils.getUserInfo(accessToken);
        return  userInfoList;
    }
}


