package com.study.cache.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.RedisSerializationContext.RedisSerializationContextBuilder
import org.springframework.data.redis.serializer.StringRedisSerializer


@Configuration
class CacheConfig {

    @Value("\${spring.redis.host}")
    private val redisHost = "host"

    @Value("\${spring.redis.port}")
    private val redisPort = 0

    @Bean
    fun reactiveRedisConnectionFactory(): ReactiveRedisConnectionFactory {
        return LettuceConnectionFactory(redisHost, redisPort)
    }

    @Bean
    fun reactiveRedisTemplate(): ReactiveRedisTemplate<String, Any> {
        val keySerializer = StringRedisSerializer()
        val valueSerializer: Jackson2JsonRedisSerializer<Any> =
            Jackson2JsonRedisSerializer(Any::class.java)
        val builder: RedisSerializationContextBuilder<String, Any> =
            RedisSerializationContext.newSerializationContext<String, Any>(keySerializer)
        val context: RedisSerializationContext<String, Any> = builder.value(valueSerializer).build()
        return ReactiveRedisTemplate(reactiveRedisConnectionFactory(), context)
    }
}