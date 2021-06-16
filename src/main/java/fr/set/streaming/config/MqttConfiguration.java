package fr.set.streaming.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "mqtt")
public class MqttConfiguration {
    private String user;
    private String password;
    private String hostname;
    private String port;
    private String clientId;
}
