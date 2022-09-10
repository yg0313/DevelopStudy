package com.study.cache.service

import com.study.cache.domain.User
import com.study.cache.repository.ReactiveRedisRepository
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

interface ReactiveRedisService {

    fun addUser(user: User) : Mono<Any>

}

@Service
class ReactiveRedisServiceImpl(private val reactiveRedisTemplate: ReactiveRedisTemplate<String, String>,
                        private val repository:ReactiveRedisRepository) : ReactiveRedisService{

    override fun addUser(user: User): Mono<Any> {
        return repository.addUser(user).flatMap {
            Mono.just(it)
        }
    }
}

