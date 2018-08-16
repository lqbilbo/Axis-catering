package com.threequick.config;

import com.threequick.catering.infra.config.CQRSInfrastructureConfig;
import com.threequick.catering.infra.config.PersistenceInfrastructureConfig;
import com.threequick.catering.infra.config.UserDefinedAggregateFactory;
import com.threequick.catering.kds.config.KdsConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

@Configuration
@ComponentScan(basePackages = "com.threequick.catering")
@ContextConfiguration
@Import({CQRSInfrastructureConfig.class, PersistenceInfrastructureConfig.class, KdsConfig.class})
public class RootConfig {
}
