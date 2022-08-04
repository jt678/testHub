package com.jt.test.service;

import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * IIotTargetDetectService
 *
 * @Author: jt
 * @Date: 2022/6/22 20:35
 */
public interface IIotTargetDetectService {
    void dealMqttMessage(String topic, MqttMessage mqttMessage);
}
