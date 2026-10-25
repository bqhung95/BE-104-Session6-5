package com.example.medicalgateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Slf4j
@Component
public class LoggingFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Lấy thông tin IP
        String ipAddress = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();

        // Lấy thông tin Path
        String path = exchange.getRequest().getPath().toString();

        // Lấy thông tin Method
        String method = exchange.getRequest().getMethod().toString();

        // In log ra console theo format yêu cầu
        System.out.println(">>> [Medical Gateway Log] Client IP: " + ipAddress +
                " | Method: " + method + " | Path: " + path);

        log.info(">>> [Medical Gateway Log] Client IP: {} | Method: {} | Path: {}", ipAddress, method, path);

        // Cho phép request tiếp tục đi đến service đích
        return chain.filter(exchange);


    }
}
