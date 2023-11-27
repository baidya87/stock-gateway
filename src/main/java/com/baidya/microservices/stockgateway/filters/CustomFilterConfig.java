package com.baidya.microservices.stockgateway.filters;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class CustomFilterConfig {

    @Bean
    public GlobalFilter responseHeader(){
        return (serverWebExchange, filterChain) ->{
           return  filterChain.filter(serverWebExchange).then(Mono.fromRunnable(()->{
                serverWebExchange.getResponse().getHeaders().add("clientRequestId", serverWebExchange.getRequest().getHeaders().get("clientRequestId").stream().findFirst().get());
            }));
        };
    }
}

