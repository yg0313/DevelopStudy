package com.study.cache.handler

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono

@Component
class ReactiveRedisHandler {

    fun redisTest(request : ServerRequest) : Mono<ServerResponse> {

        return ok().body(Mono.just("test"))
    }
}