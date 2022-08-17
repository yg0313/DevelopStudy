package com.study.cache.router

import com.study.cache.handler.ReactiveRedisHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.*

@Configuration
class ReactiveRedisRouter{

    @Autowired
    private lateinit var reactiveRedisHandler : ReactiveRedisHandler

    @Bean
    fun route(): RouterFunction<ServerResponse> = router {
        "/redis".nest {
            GET("/{id}", reactiveRedisHandler::redisTest)
        }
    }
}