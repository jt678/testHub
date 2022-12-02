package com.jt.test.demo1.runner;

import com.jt.test.demo1.common.MqttSubscriber;
import com.jt.test.demo1.service.IIotTargetDetectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author Sinliz
 */
@Component
@RequiredArgsConstructor
public class MqttStartedRunner implements ApplicationRunner {
    private final IIotTargetDetectService iotTargetDetectService;

    @Value("${jt.mqtt.SERVER_URI}")
    private String SERVER_URI;
    @Value("${jt.mqtt.CLIENT_ID}")
    private String CLIENT_ID;
    @Value("${jt.mqtt.topic}")
    private String topic;
    @Value("${jt.mqtt.statusTopic}")
    private String statusTopic;

    @Override
    public void run(ApplicationArguments args) {
        MqttSubscriber mqttSubscriber = new MqttSubscriber(SERVER_URI,CLIENT_ID,iotTargetDetectService);
        mqttSubscriber.subscribe(topic);
        mqttSubscriber.subscribe(statusTopic);
    }
}
