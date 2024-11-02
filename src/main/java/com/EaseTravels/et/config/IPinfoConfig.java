package com.EaseTravels.et.config;

import io.ipinfo.api.IPinfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IPinfoConfig {
    @Value("${ipinfo.token}")
    private String token;

    @Bean
    public IPinfo iPinfo() {
        return new IPinfo.Builder().setToken(token).build();
    }
}
