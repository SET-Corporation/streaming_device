package fr.set.streaming.services;

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
public class PlaybackService{

    Logger logger = LoggerFactory.getLogger(MessagingService.class);

    private final MediaPlayerService mediaPlayerService;
    private final MessagingService messagingService;

    private final String link = "http://localhost:";
    private final String port = "8082";

    @Autowired
    public PlaybackService(MediaPlayerService mediaPlayerService, MessagingService messagingService){
        this.mediaPlayerService = mediaPlayerService;
        this.messagingService = messagingService;
    }

    @PostConstruct
    public void init() throws MqttException {
        messagingService.subscribe("set/tv1", this::playback);
        messagingService.publish();
    }

    private void playback(String tpic, String message){
        logger.warn(message);
//        EmbeddedMediaPlayer mediaPlayer = mediaPlayerService.getNewGraphicalEmbeddedMediaPlayer();
//        mediaPlayer.prepareMedia(link.concat(port));
//        mediaPlayer.play();
    }
}
