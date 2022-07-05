package com.jt.test.utils;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * wxMessageUtils
 * 处理微信公众号发过来的消息（xml形式），将解析结果放在hashMap里
 * @Author: jt
 * @Date: 2022/7/5 14:00
 */
@Slf4j
public class wxMessageUtils {

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
        }else {
            //发送者账号
            String fromUserName = msgMap.get("FromUserName");
            //开发者账号
            String toUserName = msgMap.get("ToUserName");

            return String.format(
                    "<xml>" +
                            "<ToUserName><![CDATA[%s]]></ToUserName>" +
                            "<FromUserName><![CDATA[%s]]></FromUserName>" +
                            "<CreateTime>%s</CreateTime>" +
                            "<MsgType><![CDATA[text]]></MsgType>" +
                            "<Content><![CDATA[%s]]></Content>" + "</xml>",
                    fromUserName, toUserName, getUtcTime(),"请回复如下关键词：\n商品介绍\n客服\n人工客服\n商品链接\n商品图");
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
     * 返回语音消息给公众号
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

}
