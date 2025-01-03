/*
package com.onlybuns.isa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Omogućava CORS za sve rute, iz bilo kog izvora
        registry.addMapping("api/**")
                .allowedOrigins("http://localhost:4200")  // Postavi URL tvoje Angular aplikacije
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Omogući samo metode koje ti trebaju
                .allowedHeaders("*");  // Omogući sve zaglavlja
    }
}
*/
