package com.example.boardApi.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("OPTIONS", "GET", "PUT", "POST", "DELETE")
                .allowedHeaders("*")
                /** 응답헤더 설정  클라이언트에서 받지 못함 */
                .exposedHeaders("Authorization", "Authorization-refresh","WWW-Authenticate", "Content-Disposition")
                .allowCredentials(true)
                .maxAge(TimeUnit.DAYS.toSeconds(1));
    }
}
