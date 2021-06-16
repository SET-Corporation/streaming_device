package fr.set.streaming.services.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagingService {

    private final IMqttClient mqttClient;

    @Autowired
    public MessagingService(IMqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }
    public void subscribe(final String topic, MqttMethod mqttMethod) throws MqttException {
        mqttClient.subscribeWithResponse(topic, (tpic, msg) -> {
            String message = new String(msg.getPayload());
            mqttMethod.operate(tpic, message);
        });
    }
    public void publish() throws MqttException {
        mqttClient.publish("set/tv1", "tg".getBytes(), 0, true);
    }

}
