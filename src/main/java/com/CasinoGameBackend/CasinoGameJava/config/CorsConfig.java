package com.CasinoGameBackend.CasinoGameJava.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

//@Configuration
//public class CorsConfig {
//
//    @Bean
//    public CorsFilter corsFilter(){
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
//        config.setAllowedMethods(Collections.singletonList("*"));
//        config.setAllowedHeaders(Arrays.asList("Content-Type"));
//        config.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**",config);
//        return new CorsFilter(source);
//
//    }
//}
