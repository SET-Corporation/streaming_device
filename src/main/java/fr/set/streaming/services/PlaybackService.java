package fr.set.streaming.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import javax.annotation.PostConstruct;

@Service
public class PlaybackService{

    private final MediaPlayerService mediaPlayerService;

    private final String link = "http://localhost:";
    private final String port = "8082";

    @Autowired
    public PlaybackService(MediaPlayerService mediaPlayerService){
        this.mediaPlayerService = mediaPlayerService;
    }

    @PostConstruct
    public void init() {
        EmbeddedMediaPlayer mediaPlayer = mediaPlayerService.getNewGraphicalEmbeddedMediaPlayer();
        mediaPlayer.prepareMedia(link.concat(port));
        mediaPlayer.play();
    }
}
