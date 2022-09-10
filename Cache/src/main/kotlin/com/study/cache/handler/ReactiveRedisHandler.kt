package com.study.cache.handler

import com.study.cache.domain.User
import com.study.cache.service.ReactiveRedisService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono

@Component
class ReactiveRedisHandler(private val service : ReactiveRedisService) {

    fun addUser(request : ServerRequest) : Mono<ServerResponse> {
        val name = request.queryParam("name").toString()

        return request.bodyToMono(User::class.java).flatMap { user ->
            service.addUser(user)
        }.flatMap {
            ok().body(Mono.just(it))
        }
    }
}