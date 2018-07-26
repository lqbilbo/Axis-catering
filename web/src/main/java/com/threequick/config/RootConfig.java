package com.threequick.config;

import com.threequick.catering.infra.config.CQRSInfrastructureConfig;
import com.threequick.catering.infra.config.PersistenceInfrastructureConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CQRSInfrastructureConfig.class, PersistenceInfrastructureConfig.class})
public class RootConfig {
}
