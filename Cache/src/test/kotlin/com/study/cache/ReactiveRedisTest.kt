package com.study.cache

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.core.ReactiveValueOperations
import reactor.test.StepVerifier

@SpringBootTest
class ReactiveRedisTest() {

    @Autowired
    private lateinit var reactiveRedisTemplate: ReactiveRedisTemplate<String,String>
    private lateinit var reactiveValueOps : ReactiveValueOperations<String,String>

    @BeforeEach
    fun setup(){
        reactiveValueOps = reactiveRedisTemplate.opsForValue()
    }

    @Test
    @DisplayName("리액티브 레디스 저장")
    fun reactiveRedisSaveTest(){
        val result = reactiveValueOps.set("testKey", "testValue")
        StepVerifier.create(result).expectNext(true).verifyComplete()
    }

    @Test
    @DisplayName("저장된 값 가져오기")
    fun redisGetValues(){
        val value = reactiveValueOps.get("testKey")
        StepVerifier.create(value).expectNext("testValue").verifyComplete()
    }
}