package com.baidya.microservices.stockgateway.filters;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class FilterUtil {
    public boolean isCorelationIdPresent(HttpHeaders httpHeaders){
        return httpHeaders.get("clientRequestId")!=null;
    }

    public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String header, String value){
        return exchange.mutate().request(exchange.getRequest().mutate().header(header, value).build()).build();
    }

}
