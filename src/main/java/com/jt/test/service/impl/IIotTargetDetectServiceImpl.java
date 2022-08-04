package com.jt.test.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jt.test.common.MqttProducer;
import com.jt.test.service.IIotTargetDetectService;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * IIotTargetDetectServiceImpl
 *
 * @Author: jt
 * @Date: 2022/6/22 20:38
 */
@Service
public class IIotTargetDetectServiceImpl implements IIotTargetDetectService {


    @Value("${jt.mqtt.topic}")
    private String topic;
    @Value("${jt.mqtt.statusTopic}")
    private String statusTopic;
    @Value("${jt.mqtt.SERVER_URI}")
    private String SERVER_URI;
    @Value("${jt.mqtt.CLIENT_ID}")
    private String CLIENT_ID;

    private MqttProducer mqttProducer;

    @Override
    public void dealMqttMessage(String topic, MqttMessage mqttMessage) {
        if (mqttProducer == null) {
            mqttProducer = new MqttProducer(this.SERVER_URI,this.CLIENT_ID);
        }
        if (topic.equals(this.topic)) {
            String result = new String(mqttMessage.getPayload());
            System.out.println(result);
            if (!result.equalsIgnoreCase("hello mqtt"))
            mqttProducer.send(topic,1,true,"hello mqtt");
        } else if(topic.equals(this.statusTopic)) {
            System.out.println("haha");
        }

    }
}
