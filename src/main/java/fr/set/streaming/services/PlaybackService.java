package fr.set.streaming.services;

import fr.set.streaming.config.HttpConfiguration;
import fr.set.streaming.config.MqttConfiguration;
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
    private final MqttConfiguration mqttConfiguration;
    private EmbeddedMediaPlayer embeddedMediaPlayer;

    Logger logger = LoggerFactory.getLogger(MessagingService.class);

    @Autowired
    public PlaybackService(HttpConfiguration httpConfiguration, MessagingService messagingService, MqttConfiguration mqttConfiguration, MediaPlayerService mediaPlayerService) {
        this.httpConfiguration = httpConfiguration;
        this.messagingService = messagingService;
        this.embeddedMediaPlayer = null;
        this.mqttConfiguration = mqttConfiguration;
        this.embeddedMediaPlayer = mediaPlayerService.getNewGraphicalEmbeddedMediaPlayer();
    }

    @PostConstruct
    public void init() throws MqttException {
        messagingService.subscribe(mqttConfiguration.getTopic(), this::playback, 2);
    }

    private void playback(String tpic, String message) {
        if(message.equals("0")){
            embeddedMediaPlayer.stop();
            logger.info("stopping playlist..");
        }
        else {
            embeddedMediaPlayer.prepareMedia(httpConfiguration.getHostname().concat(message));
            embeddedMediaPlayer.play();
            logger.info("start playlist..");
        }
    }
}
