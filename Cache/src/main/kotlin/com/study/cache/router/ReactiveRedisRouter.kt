package com.study.cache.router

import com.study.cache.handler.ReactiveRedisHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.*

@Configuration
class ReactiveRedisRouter(private val reactiveRedisHandler : ReactiveRedisHandler){

    @Bean
    fun route(): RouterFunction<ServerResponse> = router {
        "/redis".nest {
            POST("/addUser", reactiveRedisHandler::addUser)
            GET("/{id}", reactiveRedisHandler::getUser)
        }
    }
}