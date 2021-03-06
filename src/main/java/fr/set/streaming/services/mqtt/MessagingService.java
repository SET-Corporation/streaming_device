package fr.set.streaming.services.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagingService {

    private final IMqttClient mqttClient;

    @Autowired
    public MessagingService(IMqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    public void subscribe(final String topic, MqttMethod mqttMethod, int qos) throws MqttException {
        mqttClient.subscribeWithResponse(topic, qos, (tpic, msg) -> {
            String message = new String(msg.getPayload());
            mqttMethod.operate(tpic, message);
        });
    }
}
