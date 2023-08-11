package com.sisweb.api.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Slf4j
@Configuration
@Profile("dev")
@RequiredArgsConstructor
public class ProfileDevConfig {

    private final DbInstancia dbInstancia;

    @Bean
    public void instantiateDataBase() {

        log.info("INSTANCIANDO PROFILE DEV");

        dbInstancia.instanciandoPerfis();
    }
}
