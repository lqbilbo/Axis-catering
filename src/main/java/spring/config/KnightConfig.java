package spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.inject.BraveKnight;
import spring.inject.Knight;
import spring.inject.Quest;
import spring.inject.SlayDragonQuest;

@Configuration
public class KnightConfig {

    //bean定义
    @Bean
    public Knight knight() {

        return new BraveKnight(quest());
    }

    @Bean
    public Quest quest() {
        return new SlayDragonQuest(System.out);
    }
}
