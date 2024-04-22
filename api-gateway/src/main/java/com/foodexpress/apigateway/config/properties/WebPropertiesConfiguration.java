package com.foodexpress.apigateway.config.properties;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebPropertiesConfiguration {

    @Bean
     public WebProperties.Resources resources(){
        return new WebProperties.Resources();
    }
}
