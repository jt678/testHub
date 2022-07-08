package com.jt.test.utils;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jt.test.domain.UserInfo;
import com.jt.test.domain.vo.UserVO;
import com.jt.test.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * wxMessageUtils
 * 处理微信公众号发过来的消息（xml形式），将解析结果放在hashMap里
 * @Author: jt
 * @Date: 2022/7/5 14:00
 */
@Slf4j
@Component
public class WxMessageUtils {

    @Autowired
    private  UserInfoService userInfoService;
    private static WxMessageUtils wxMessageUtils;

    @PostConstruct
    public void init(){
        wxMessageUtils = this;
        wxMessageUtils.userInfoService = this.userInfoService;
    }


    //目前我的个人微信公众号没有调用这些接口的权限，需要腾讯灰度测试后内部进行邀请
    private static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    private static String OPENID_LIST_URL ="https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
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
            //此处打断点可看到，再rootElement里的内容明明有12个，但是最终得到elements只有6个，是因为每个元素后面带有一个“\n”换行符，所以是12个
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
     * 获取用户信息，并将最新数据新增保存到本地库
     */

    public static List<UserVO> getUserInfo(String accessToken){

        //获取用户列表的openIdList,NEXT_OPENID------第一个拉取的openId，不填默认从头开始拉取，这里是测试，所以默认不填
        //当关注量很大时，可以通过next_openId多次拉取，next_oendId会在查询中返回
        JSONObject jsonObject = JSONObject.parseObject(HttpUtil.get(OPENID_LIST_URL.replace("ACCESS_TOKEN", accessToken).replace("NEXT_OPENID","")));
        //获取用户OPENID数组转化成List
        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("openid");


        List<String> openIdList = JSONObject.parseArray(String.valueOf(jsonArray),String.class) ;

        ArrayList<UserVO> userInfoList = Lists.newArrayList();
        ArrayList<UserInfo> userInfos = Lists.newArrayList();
        Map<String,Long> idMap = new HashMap<>(); //尝试用stream构建map
        List<UserInfo> allUserInfoList = wxMessageUtils.userInfoService.list();

        if (!allUserInfoList.isEmpty()){
         idMap = allUserInfoList.stream().collect(Collectors.toMap(UserInfo::getOpenid, UserInfo::getId));
        }


        //有优化的点，微信Api提供了一个批量查询基础信息的接口，如果用for循环每次去请求有点慢
        for (String openId : openIdList) {
            //构建用户信息的接口，填充参数
            String userInfoUrl = USER_INFO_URL.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
            JSONObject infoJsonObject = JSONObject.parseObject(HttpUtil.get(userInfoUrl));

            //这里有个坑，wx传来的时间戳是到秒的，jsonobject.tojavaObject自动解析到毫秒会错误,要手动处理下在解析前把10位时间戳凭借成13位的
            String  subscribe = infoJsonObject.getInteger("subscribe_time").toString();
            String i = subscribe.concat("000");
            infoJsonObject.put("subscribe_time",i);
            //把需要的信息返回给前端
            UserVO userVO = JSONObject.toJavaObject(infoJsonObject, UserVO.class);
            userInfoList.add(userVO);

            UserInfo userInfo = JSONObject.toJavaObject(infoJsonObject, UserInfo.class);
            //saveOrUpdate,但是调微信接口返回是必没有Id的，只能用openId做唯一标识，先把数据库的OpenId和Id查出来做个map，用来判断有没有
            //数据库没有，set新生成的id然后save进数据库，有则拿到id然后set原id做update操作

            String id = String.valueOf(idMap.get(userInfo.getOpenid()));
            Date subscribeTime = userInfo.getSubscribeTime();
            if (StringUtils.isEmpty(id) && id.equals("")){
                userInfo.setId(SqlIdUtils.getId());
            }else {
                userInfo.setId(idMap.get(userInfo.getOpenid()));
            }
            userInfos.add(userInfo);
        }
        wxMessageUtils.userInfoService.saveOrUpdateBatch(userInfos);
        //最后得到的时用户信息的一个LIST,里面有OpenId和UnionId做唯一标识
        return  userInfoList;
    }


}
