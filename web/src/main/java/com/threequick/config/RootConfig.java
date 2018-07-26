package com.threequick.config;

import com.threequick.catering.infra.config.PersistenceInfrastructureConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "com.threequick.catering")
@Import({PersistenceInfrastructureConfig.class})
public class RootConfig {
}
