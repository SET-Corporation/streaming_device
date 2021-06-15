package fr.set.streaming;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class StreamingDeviceApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(StreamingDeviceApplication.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);
    }
}
