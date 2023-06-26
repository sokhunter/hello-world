package com.coralogix.calculator.config;

import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;

@TestConfiguration
public class R2dbcConfig {
    @Bean
    public R2dbcEntityTemplate r2dbcEntityTemplate() {
        H2ConnectionConfiguration configuration = H2ConnectionConfiguration
                .builder()
                .url("jdbc:h2:mem:test_db")
                .username("user-test")
                .password("pass-test")
                .build();
        ConnectionFactory connectionFactory = new H2ConnectionFactory(configuration);
        return new R2dbcEntityTemplate(connectionFactory);
    }
}
