package soundsystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import soundsystem.CDPlayer;
import soundsystem.CompactDisc;
import soundsystem.SgtPeppers;

@Configuration
@Import(SoundSystemConfig.class)
//@ComponentScan
public class CDPlayerConfig {

    @Bean
    @Primary
    public CDPlayer cdPlayer(CompactDisc compactDisc) {
        return new CDPlayer(compactDisc);
    }
}
