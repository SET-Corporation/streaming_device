package fr.set.streaming.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "set.vlc.config")
public class VlcConfiguration {
    private String vlcPath;

    public String getVlcPath() {
        return vlcPath;
    }

    public void setVlcPath(String vlcPath) {
        this.vlcPath = vlcPath;
    }
}
