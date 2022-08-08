package com.jt.test.helper;

import com.jt.test.common.HttpResult;
import com.jt.test.domain.bo.SendBO;
import com.jt.test.utils.myEnum.Season;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * MailHelper
 *
 * @Author: jt
 * @Date: 2022/8/8 15:00
 */
@Service
public class MailHelper {
    @Autowired
    private JavaMailSender javaMailSender;

    public HttpResult send() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("510791331@qq.com");
        simpleMailMessage.setTo("1305361263@qq.com");
        simpleMailMessage.setSubject("见面提醒邮件！！！");

        LocalDateTime end = LocalDateTime.of(2022, 8, 8, 17, 30, 0, 0);
        LocalDateTime now = LocalDateTime.now();

        String formatNow = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(now);

        Duration between = Duration.between(now, end);
        //注意要取余，不然得到的相差分钟差
        long minutes = between.toMinutes() % 60l;
        long hours = between.toHours();

        simpleMailMessage.setText("当前时间：" + formatNow + "还有" + hours + "小时" + minutes + "分钟");

        javaMailSender.send(simpleMailMessage);
        return HttpResult.success("发送成功" + simpleMailMessage.getText());
    }

    public HttpResult sendBatch(SendBO sendBO) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //设置固定信息
        simpleMailMessage.setFrom("510791331@qq.com");
        simpleMailMessage.setSubject("群发测试");

        //获取接收人和内容对应map
        List<String> toUserAddress = Arrays.asList(sendBO.getToUserAddress().split(";"));
        List<String> content = Arrays.asList(sendBO.getContent().split(";"));
        HashMap<String, String> userInfoHashMap = new HashMap<>();
        for (int i = 0; i < toUserAddress.size(); i++) {
            String key = toUserAddress.get(i);
            String value = content.get(i);
            userInfoHashMap.put(key, value);
        }

        int i = 0;
        //发送执行程序
        for (String userAddress : toUserAddress) {
            simpleMailMessage.setTo(userAddress);
            simpleMailMessage.setText("亲爱的用户：" + userInfoHashMap.get(userAddress) + "\n" + "这是一条群发测试邮件,您是第" + (i + 1) + "个收到此邮件的，您的账户为：" + userAddress);
            javaMailSender.send(simpleMailMessage);
            i++;
        }
        System.out.println("现在是"+ Season.SUMMER.getSeason()+"气候"+Season.SUMMER.getDescription());
        return HttpResult.success("发送成功数量：" + i);
    }
}
