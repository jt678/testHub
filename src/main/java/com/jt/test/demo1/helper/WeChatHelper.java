package com.jt.test.demo1.helper;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jt.test.demo1.domain.entity.UserInfo;
import com.jt.test.demo1.domain.vo.UserVO;
import com.jt.test.demo1.service.UserInfoService;
import com.jt.test.demo1.utils.WxMessageUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.util.crypto.SHA1;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

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
    private String accessToken;
    private static String USER_LIST_URL ="https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 每一个小时获取一次AccessToken
     */
    @Scheduled(fixedDelay = 3600*1000)
    @Async
    public void sync(){
        String accessTokennow = WxMessageUtils.getAccessToken(appId,appSecret);
        accessToken = accessTokennow;
        if (accessToken != null){
        log.info("自动获取到最新的AccessToken："+accessToken+"     (有效时间为一个半小时)");}

    }

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
            Map<String, String> map = WxMessageUtils.getClientMsg(request);
            System.out.println("开始构造消息");
            String result = "";
            result = WxMessageUtils.buildXml(map);

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

    /**
     * 获取用户基本信息（还在关注中0），并将最新数据保存更新到本地库
     * @return
     */
    public List<UserVO> getBaseInfo() {

        List<UserVO> userInfoList = WxMessageUtils.getUserInfo(accessToken);
        return  userInfoList;
    }

    /**
     * 基础根据openID群发内容----数据里有的，且符合条件的才会发
     * （测试条件：只推给关注来源是其他人推送名片关注的）
     * 即subscribe_scene为“ADD_SCENE_PROFILE_CARD”
     * @return
     */

    public String groupSending() {
        //1，配置
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId("wxf9fc4558c1f2812e");
        wxStorage.setSecret("bbb816109367824cf2f96bae0e791100");
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);

        //2,推送消息
        //获取所有的openIdList
        JSONObject jsonObject = JSONObject.parseObject(HttpUtil.get(USER_LIST_URL.replace("ACCESS_TOKEN", accessToken).replace("NEXT_OPENID","")));
        //获取用户OPENID数组转化成List
        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("openid");


        List<String> openIdList = JSONObject.parseArray(String.valueOf(jsonArray),String.class) ;

        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setTemplateId("WJoT0B_n_VqbHIp8gX9loPlTUJTfBITcd_RQY7wV0Iw");//模版id
        templateMessage.setUrl("http://www.10010.com/net5/011/");//点击模版消息要访问的网址
        templateMessage.addData(new WxMpTemplateData("name","jt","#8B0000"))
                .addData(new WxMpTemplateData("tel_num","181****8417","#FFD700"))
                .addData(new WxMpTemplateData("money","-1￥","#FFD700"))
                .addData(new WxMpTemplateData("多余信息测试","11111"));
        //3,如果是正式版发送模版消息，这里需要配置你的信息
//                templateMessage.addData(new WxMpTemplateData("name", "value", "#FF00FF"));
//                templateMessage.addData(new WxMpTemplateData(name2, value2, color2));

        //测试：为了满足测试只给特定人群发送，组件一个Map
        Map<String, String> subscribeSceneMap = userInfoService.list().stream().collect(Collectors.toMap(UserInfo::getOpenid, UserInfo::getSubscribeScene));
        try {

            for (String openId : openIdList) {
                String allowed = "ADD_SCENE_QR_CODE";
                if (allowed.equals(subscribeSceneMap.get(openId))){
                templateMessage.setToUser(openId);//要推送的用户openid
                wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);}
            }

            return "推送成功";
        } catch (Exception e) {
            System.out.println("推送失败：" + e.getMessage());
            e.printStackTrace();
            return "推送失败";
        }
    }

}



