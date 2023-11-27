package com.baidya.microservices.stockgateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class TrackingFilter implements GlobalFilter {

    private static final Logger logger = LoggerFactory.getLogger(TrackingFilter.class);
    private final FilterUtil filterUtil;

    public TrackingFilter(FilterUtil filterUtil) {
        this.filterUtil = filterUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders httpHeaders = exchange.getRequest().getHeaders();
        if(filterUtil.isCorelationIdPresent(httpHeaders)){
            logger.info("Co-relation ID found {}", httpHeaders.get("clientRequestId"));
        }else{
            filterUtil.setRequestHeader(exchange, "clientRequestId", UUID.randomUUID().toString());
            logger.info("co-relationId generated {}", exchange.getRequest().getHeaders().get("clientRequestId"));
        }
        return chain.filter(exchange);
    }
}
