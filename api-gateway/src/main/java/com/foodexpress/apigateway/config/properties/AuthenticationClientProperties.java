package com.foodexpress.apigateway.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "authentication.client")
@Getter
@Setter
public class AuthenticationClientProperties{
    private String baseUrl;
    private String name;
}
