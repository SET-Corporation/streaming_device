package fr.set.streaming.config;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MqttConfiguration.class)
public class MqttConnectConfig {

    private final MqttConfiguration mqttConfiguration;

    @Autowired
    public MqttConnectConfig(MqttConfiguration mqttConfiguration) {
        this.mqttConfiguration = mqttConfiguration;
    }

    @Bean
    public MqttConnectOptions mqttConnectOptions() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setUserName(mqttConfiguration.getUser());
        mqttConnectOptions.setPassword(mqttConfiguration.getPassword().toCharArray());
        return mqttConnectOptions;
    }

    @Bean
    public IMqttClient mqttClient() throws MqttException {
        IMqttClient mqttClient = new MqttClient("tcp://" + mqttConfiguration.getHostname() + ":" + mqttConfiguration.getPort(), mqttConfiguration.getClientId());
        mqttClient.connect(mqttConnectOptions());
        return mqttClient;
    }
}
