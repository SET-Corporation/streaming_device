package fr.set.streaming.services.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.x.XFullScreenStrategy;

import java.awt.*;

@Service
public class MediaPlayerService {

    private final MediaPlayerFactory mediaPlayerFactory;
    private final GraphicalService graphicalService;

    @Autowired
    public MediaPlayerService(MediaPlayerFactory mediaPlayerFactory, GraphicalService graphicalService) {
        this.mediaPlayerFactory = mediaPlayerFactory;
        this.graphicalService = graphicalService;
    }

    public EmbeddedMediaPlayer getNewGraphicalEmbeddedMediaPlayer() {
        Canvas canvas = graphicalService.getCanvas();
        EmbeddedMediaPlayer embeddedMediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer(new XFullScreenStrategy(graphicalService.getJframe(canvas)));
        embeddedMediaPlayer.setVideoSurface(mediaPlayerFactory.newVideoSurface(canvas));
        embeddedMediaPlayer.toggleFullScreen();
        embeddedMediaPlayer.setEnableKeyInputHandling(false);
        embeddedMediaPlayer.setEnableMouseInputHandling(false);
        return embeddedMediaPlayer;
    }

}
