package com.jt.test.demo1.common;


import com.jt.test.demo1.service.IIotTargetDetectService;
import com.jt.test.demo1.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;

@RequiredArgsConstructor
@Slf4j
public class MqttSubscriber {

    private MqttClient mqttClient;
    private IIotTargetDetectService iIotTargetDetectService;
    @Value("${jt.mqtt.USER_NAME}")
    private String USER_NAME;
    @Value("${jt.mqtt.PASS_WORD}")
    private String  PASS_WORD;

    MqttConnectOptions connOpts = new MqttConnectOptions();
    public MqttSubscriber(String SERVER_URI, String CLIENT_ID, IIotTargetDetectService iIotTargetDetectService) {
        try {
            MemoryPersistence persistence = new MemoryPersistence();
            mqttClient = new MqttClient(SERVER_URI, CLIENT_ID, persistence);
            connOpts.setCleanSession(true);
            connOpts.setConnectionTimeout(10);
            connOpts.setKeepAliveInterval(90);
            connOpts.setAutomaticReconnect(true);
            connOpts.setUserName(USER_NAME);
            if (!StringUtils.isEmpty(PASS_WORD) || PASS_WORD != null)
            {char[] psw = PASS_WORD.toCharArray();
            connOpts.setPassword(psw);}
            mqttClient.connect(connOpts);
            this.iIotTargetDetectService = iIotTargetDetectService;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void subscribe(String topic) {
        if (mqttClient == null) {
            return;
        }
        try {
            mqttClient.subscribe(topic);
            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {
//                    System.out.println("连接丢失");
                    throwable.printStackTrace();
                    boolean flag = true;
                    while (flag) {
                        try {
                            Thread.sleep(10000);
                            if(mqttClient.isConnected()){
                                log.error("mqtt已连接");
                                mqttClient.subscribe(topic);
                                flag = false;
                            } else if (null != mqttClient && !mqttClient.isConnected()) {
                                log.error("尝试重新连接");
                                mqttClient.reconnect();

                            } else {
                                log.error("尝试建⽴新连接");
                                mqttClient.connect(connOpts);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                    iIotTargetDetectService.dealMqttMessage(topic, mqttMessage);
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
//                    System.out.println("delivery isComplete:" + iMqttDeliveryToken.isComplete());
                }
            });
        } catch (MqttException e) {
//            System.out.println(e.getMessage());
        }
    }

}
