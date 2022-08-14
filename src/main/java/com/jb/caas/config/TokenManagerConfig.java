package com.jb.caas.config;

/*
 * copyrights @ fadi
 */

import com.jb.caas.security.Information;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
public class TokenManagerConfig {

    @Bean
    public Map<UUID, Information> map() {
        return new HashMap<>();
    }
}