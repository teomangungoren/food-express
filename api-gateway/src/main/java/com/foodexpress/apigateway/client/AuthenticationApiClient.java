package com.foodexpress.apigateway.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthenticationApiClient {
    private final WebClient authenticationClient;



    public Mono<Boolean> validateToken(String token)
    {
        return authenticationClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("localhost")
                        .port(8080)
                        .path("api/v1/users/validateToken")
                        .build()
                )
                .header("Authorization",token)
                .retrieve()
                .bodyToMono(Boolean.class);
    }
}
