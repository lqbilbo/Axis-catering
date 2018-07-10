package spring.config.concert;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import spring.aop.concert.Audience;

@Configuration
@EnableAspectJAutoProxy
@Component
public class ConcertConfig {

    @Bean
    public Audience audience() {
        return new Audience();
    }
}
