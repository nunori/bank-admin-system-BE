package com.im.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("Configuring CORS");
        registry.addMapping("/**")
//                .allowedOriginPatterns("/*")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
//                .allowedHeaders("Authorization", "Content-Type", "X-Requested-With", "Accept")
                .allowedHeaders("*")
//                .exposedHeaders("Authorization")
                .allowCredentials(true)
                .maxAge(3600);
    }
}