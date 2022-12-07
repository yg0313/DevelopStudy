package com.example.apigatewayservice.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomFilter extends AbstractGatewayFilterFactory {

    @Override
    public GatewayFilter apply(Object config) {
        //custom preFilter
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Custom preFilter: requestId -> {}", request.getId());

            //custom postFilter
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("custom postFilter: responseCode -> {}", response.getStatusCode());
            }));
        };
    }

    /**
    * 추가 설정 및 코드가 필요한 경우 이너클래스를 만들어
     * 생성자 과정에서 해당 클래스 추가하여 오버라이딩 가능.
    */
//    public CustomFilter() {
//        super(Config.class);
//    }
//    @Override
//    public GatewayFilter apply(Config config) {
//        //custom preFilter
//        return (exchange, chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//            ServerHttpResponse response = exchange.getResponse();
//
//            log.info("Custom preFilter: requestId -> {}", request.getId());
//
//            //custom postFilter
//            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
//                log.info("custom postFilter: responseCode -> {}", response.getStatusCode());
//            }));
//        };
//    }
//    public static class Config{
//        //put the configuration properties
//    }
}
