package fr.set.streaming.services.mqtt;

import inet.ipaddr.AddressStringException;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.net.UnknownHostException;

public interface MqttMethod {
    void operate(String topic, String message) throws AddressStringException, UnknownHostException, MqttException;
}
