package com.threequick.catering.infra.config;

import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactoryBean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
@ComponentScan("com.threequick.catering.*")
public class PersistenceInfrastructureConfig {

    @Bean
    @Profile("hsqldb")
    public EmbeddedDatabaseFactoryBean dataSource() {
        EmbeddedDatabaseFactoryBean embeddedDatabaseFactoryBean = new EmbeddedDatabaseFactoryBean();
        embeddedDatabaseFactoryBean.setDatabaseType(EmbeddedDatabaseType.HSQL);

        return embeddedDatabaseFactoryBean;
    }

}
