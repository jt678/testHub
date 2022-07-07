package com.jt.test.utils;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jt.test.domain.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * wxMessageUtils
 * 处理微信公众号发过来的消息（xml形式），将解析结果放在hashMap里
 * @Author: jt
 * @Date: 2022/7/5 14:00
 */
@Slf4j
public class WxMessageUtils {


    //目前我的个人微信公众号没有调用这些接口的权限，需要腾讯灰度测试后内部进行邀请
    private static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    private static String USER_LIST_URL ="https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
    private static String USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";


    /**
     * 获取公众号发来的信息（有点像爬虫）
     */
    public static Map<String,String> getClientMsg(HttpServletRequest request){

        log.info("========获取输入流开始处理信息========");
        HashMap<String, String> msgMap = new HashMap<>();
        InputStream inputStream = null;

        try {
            inputStream=request.getInputStream();
            //使用SAXReader来解析xml文件
            SAXReader saxReader = new SAXReader();
            //通过read读取xml文件，返回Document格式对象
            Document document = saxReader.read(inputStream);
            //获得xml解析后的根节点，再获取下面所有的根节点下面的子节点
            List<Element> elements = document.getRootElement().elements();
            for (Element element : elements) {
                Element element0 = elements.get(0);
                log.info(element.getName()+"||"+ element.getText());
                msgMap.put(element.getName(),element.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                //用完流要记得关闭，不然服务器会炸
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return msgMap;
    }

    /**
     * 根据消息类型 构造返回结构
     */
    public static String buildXml(Map<String ,String> msgMap){
        //获取msgType
        String msgType = String.valueOf(msgMap.get("MsgType"));
        //获取用户输入内容
        String content = String.valueOf(msgMap.get("Content"));
        log.info("消息类型是"+msgType);
        String result = null;
        if (msgType.toUpperCase().equals("TEXT")){
            if (content.equals("文本")){
                result = buildReply(msgMap,"这是一条测试文本内容");
            }else if (content.equals("商品介绍")){
                result = buildReply(msgMap,"商品不好，别买");
            }else if (content.equals("客服")){
                result = buildReply(msgMap,"来了老弟！");
            }else if (content.equals("人工客服")){
                result = buildReply(msgMap,"没有，联系133******77");
            }else if (content.equals("商品链接")){
                result = buildReply(msgMap,"www.baidu.com");
            }
            else if (content.equals("商品图")){
                result = buildReply(msgMap,"图");
            }
            else if (content.equals("傻逼")){
                result = buildReply(msgMap,"你才是傻逼");
            }

            else
            result = buildReply(msgMap,"正在开发中，别急");
        }else if(msgType.toUpperCase().equals("EVENT")) {
            //发送者账号
            String fromUserName = msgMap.get("FromUserName");
            //开发者账号
            String toUserName = msgMap.get("ToUserName");
            //第一次关注，事件为event，发送这条消息
            return String.format(
                    "<xml>" +
                            "<ToUserName><![CDATA[%s]]></ToUserName>" +
                            "<FromUserName><![CDATA[%s]]></FromUserName>" +
                            "<CreateTime>%s</CreateTime>" +
                            "<MsgType><![CDATA[text]]></MsgType>" +
                            "<Content><![CDATA[%s]]></Content>" + "</xml>",
                    fromUserName, toUserName, getUtcTime(),
//                    "请回复如下关键词：\n商品介绍\n客服\n人工客服\n商品链接\n商品图"
                    "你好，这是一个测试公众号，如需联系请回复所在城市和手机号，方便我们的人员联系，同时你可以回复如下关键词：\n商品介绍\n客服\n人工客服\n商品链接\n商品图\n来获取一些简单的支持"
            );
        }
        return result;
    }

    /**
     * 构造返回消息
     */
    public static String buildReply(Map<String,String>msgMap,String replyContent){
        //发送者账号
        String fromUserName = msgMap.get("FromUserName");
        //开发者账号
        String toUserName = msgMap.get("ToUserName");
        //最后返回给微信服务器的是这个东西，再返回给客户端
        return String.format(
                "<xml>" +
                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
                        "<CreateTime>%s</CreateTime>" +
                        "<MsgType><![CDATA[text]]></MsgType>" +
                        "<Content><![CDATA[%s]]></Content>" + "</xml>",
                fromUserName, toUserName, getUtcTime(), replyContent);
    }

    /**
     * 获取当前时间
     * @return
     */
    private static String getUtcTime() {
        // 如果不需要格式,可直接用dt,dt就是当前系统时间
        Date dt = new Date();
        // 设置显示格式
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String nowTime = df.format(dt);
        long dd = (long) 0;
        try {
            dd = df.parse(nowTime).getTime();
        } catch (Exception e) {

        }
        log.info("当前时间："+String.valueOf(dd));
        return String.valueOf(dd);
    }

    /**
     * 返回语音消息给公众号(还未写完)
     */
    private static String sendVoiceMessage(Map<String,String> msgMap){
        //发送者账号
        String fromUserName = msgMap.get("FromUserName");
        //开发者账号
        String toUserName = msgMap.get("ToUserName");
        String mediaId = msgMap.get("MediaId");
        return String.format(
                "<xml>" +
                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
                        "<CreateTime>%s</CreateTime>" +
                        "<MsgType><![CDATA[voice]]></MsgType>" +
                        "<Voice>" +
                        "   <MediaId><![CDATA[%s]]></MediaId>" +
                        "</Voice>" +
                        "</xml>",
                fromUserName,toUserName, getUtcTime(),mediaId
               );
    }

    /**
     * 获取Access Token
     */

    public static String getAccessToken(String appId,String appSecret ){

        //拼接请求地址
        String accessTokenUrl = ACCESS_TOKEN_URL.replace("APPID",appId).replace("APPSECRET",appSecret);
        JSONObject jsonObject = JSONObject.parseObject(HttpUtil.get(accessTokenUrl));
        String accessToken = String.valueOf(jsonObject.get("access_token"));
        return  accessToken;
    }

    /**
     * 获取用户信息
     */
    public static List<UserVO> getUserInfo(String accessToken){
        //获取用户列表的openIdList,NEXT_OPENID------第一个拉取的openId，不填默认从头开始拉取，这里是测试，所以默认不填
        JSONObject jsonObject = JSONObject.parseObject(HttpUtil.get(USER_LIST_URL.replace("ACCESS_TOKEN", accessToken).replace("NEXT_OPENID","")));
        //获取用户OPENID数组转化成List
        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("openid");


        List<String> openIdList = JSONObject.parseArray(String.valueOf(jsonArray),String.class) ;

        ArrayList<UserVO> userInfoList = Lists.newArrayList();

        //有优化的点，微信Api提供了一个批量查询基础信息的接口，如果用for循环每次去请求有点慢
        for (String openId : openIdList) {
            //构建用户信息的接口，填充参数
            String userInfoUrl = USER_INFO_URL.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
            JSONObject infoJsonObject = JSONObject.parseObject(HttpUtil.get(userInfoUrl));
            //此字段标识用户是否还在关注，为0时代表没有关注公众号拉取不到其余数据，这里加一层判断是做测试无实际作用，获取到的subsribe必为1
            Integer subscribe = infoJsonObject.getInteger("subscribe");
            if(subscribe.equals(1)){
                UserVO userVO = JSONObject.toJavaObject(infoJsonObject, UserVO.class);
                userInfoList.add(userVO);
            }
        }
        //最后得到的时用户信息的一个LIST,里面有OpenId和UnionId做唯一标识
        return  userInfoList;
    }


}
