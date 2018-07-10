package soundsystem.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import soundsystem.CDPlayer;
import soundsystem.CompactDisc;
import soundsystem.SgtPeppers;

@Configuration
@Import(SoundSystemConfig.class)
public class CDConfig {
    @Bean
    @Qualifier("cd")
    public CompactDisc compactDisc(CDPlayer cdPlayer) {
        cdPlayer.play();
        return new SgtPeppers();
    }
}
