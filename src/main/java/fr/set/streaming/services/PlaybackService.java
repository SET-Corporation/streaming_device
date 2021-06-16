package fr.set.streaming.services;

import fr.set.streaming.config.HttpConfiguration;
import fr.set.streaming.services.mqtt.MessagingService;
import fr.set.streaming.services.player.MediaPlayerService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import javax.annotation.PostConstruct;

@Service
public class PlaybackService {

    private final HttpConfiguration httpConfiguration;
    private final MessagingService messagingService;
    private final EmbeddedMediaPlayer embeddedMediaPlayer;
    Logger logger = LoggerFactory.getLogger(MessagingService.class);

    @Autowired
    public PlaybackService(HttpConfiguration httpConfiguration, MediaPlayerService mediaPlayerService, MessagingService messagingService) {
        this.httpConfiguration = httpConfiguration;
        this.messagingService = messagingService;
        this.embeddedMediaPlayer = mediaPlayerService.getNewGraphicalEmbeddedMediaPlayer();
    }

    @PostConstruct
    public void init() throws MqttException {
        messagingService.subscribe("set/tv1", this::playback);
        messagingService.publish();
    }

    private void playback(String tpic, String message) {
        logger.warn(message);
        embeddedMediaPlayer.prepareMedia(httpConfiguration.getHostname().concat(message));
        embeddedMediaPlayer.play();
    }
}
