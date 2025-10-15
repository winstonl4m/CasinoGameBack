package com.CasinoGameBackend.CasinoGameJava.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PublicPathConfig {

    @Bean
    public List<String> publicPaths(){
        return List.of("/api/games/**",
                "/api/auth/**","/h2-console/**"

        );
    }
}
