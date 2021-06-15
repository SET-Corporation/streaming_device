package fr.set.streaming.config;

import com.sun.jna.NativeLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

@Configuration
@EnableConfigurationProperties(VlcConfiguration.class)
public class VlcLibConfig {

    private final VlcConfiguration vlcConfiguration;

    @Autowired
    public VlcLibConfig(VlcConfiguration vlcConfiguration) {
        this.vlcConfiguration = vlcConfiguration;
    }

    @Bean
    MediaPlayerFactory mediaPlayerFactory() {
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), vlcConfiguration.getVlcPath());
        return new MediaPlayerFactory();
    }
}
