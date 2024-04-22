package com.foodexpress.apigateway.filter;


import com.foodexpress.apigateway.client.AuthenticationApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;


@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements GlobalFilter {
    private final AuthenticationApiClient authenticationApiClient;

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AuthenticationFilter.class);

    public static final List<String> AUTH_EXCLUDE_PATHS = List.of("/api/v1/users/authenticate", "/api/v1/users/register");
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestPath = exchange.getRequest().getPath().toString();
         System.out.println("Request path is " + requestPath);
        if(AUTH_EXCLUDE_PATHS.contains(requestPath))
        {
            log.info("Request path is in exclude list");
            return chain.filter(exchange);
        }
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        if(token == null || token.isEmpty())
        {
            exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        return authenticationApiClient.validateToken(token)
                .flatMap(isValid -> {
                    if(isValid)
                    {
                        return chain.filter(exchange);
                    }
                    exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                });
    }
}
