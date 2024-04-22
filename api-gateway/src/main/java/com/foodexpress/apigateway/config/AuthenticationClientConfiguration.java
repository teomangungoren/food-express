package com.foodexpress.apigateway.config;

import com.foodexpress.apigateway.config.properties.AuthenticationClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AuthenticationClientConfiguration{

    @Bean
    public WebClient authenticationClient(AuthenticationClientProperties authenticationClientProperties){
       return WebClient.builder()
                .baseUrl(authenticationClientProperties.getBaseUrl())
                .build();
    }

}
