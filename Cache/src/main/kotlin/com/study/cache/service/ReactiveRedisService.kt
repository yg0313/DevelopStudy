package com.study.cache.service

import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service

@Service
class ReactiveRedisService(private val redisTemplate: ReactiveRedisTemplate<String,Any>) {

}