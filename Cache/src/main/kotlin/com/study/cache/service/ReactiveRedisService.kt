package com.study.cache.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.study.cache.domain.User
import com.study.cache.repository.ReactiveRedisRepository
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.core.ReactiveValueOperations
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import java.time.Duration

interface ReactiveRedisService {

    fun addUser(user: User) : Mono<Any>

    fun getUser(userId: String) : Mono<Any>
}

@Service
class ReactiveRedisServiceImpl(private val reactiveRedisTemplate: ReactiveRedisTemplate<String, Any>,
                        private val repository:ReactiveRedisRepository) : ReactiveRedisService{

    private lateinit var redisValueOps : ReactiveValueOperations<String,Any>

    override fun addUser(user: User): Mono<Any> {
        return repository.addUser(user).flatMap {
            Mono.just(it)
        }
    }

    override fun getUser(userId: String): Mono<Any> {
        redisValueOps = reactiveRedisTemplate.opsForValue()

        val query = Query().addCriteria(Criteria().and("userId").`is`(userId))

        return redisValueOps.get(userId).switchIfEmpty(
            repository.getUser(query).flatMap {user ->
                redisValueOps.set(userId, user, Duration.ofMinutes(30)).subscribe()
                Mono.just(user)
            }
        ).flatMap {
            Mono.just(it)
        }
    }
}

