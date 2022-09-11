# Reactive Redis & Caffeine Cache

### 환경  
- jdk 17  
- springBoot 2.7.2  
- spring webflux
- MonogoDB  

** 캐시 적용 과정 및 테스트를 위한 코드이므로 유효성, exception 처리에 대한 코드는 생략

## Reactive Redis  
** 로컬 레디스 환경에서 진행  
```kotlin
//dependency
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis-reactive</artifactId>
</dependency>
```

```kotlin
/**
* Bean 설정
*/
@Value("\${spring.redis.host}")
private val redisHost = "127.0.0.1" //로컬 환경

@Value("\${spring.redis.port}")
private val redisPort = 6379 //redis 기본 포트번호

@Bean
fun reactiveRedisConnectionFactory(): ReactiveRedisConnectionFactory {
    return LettuceConnectionFactory(redisHost, redisPort)
}

/**
* 레디스 템플릿
* key, value 값으로 설정
* value를 Any타입으로 하여, 모든 Object에 대하여 값을 사용할 수 있도록 하기위해
*/
@Bean
fun reactiveRedisTemplate(): ReactiveRedisTemplate<String, Any> {
    val keySerializer = StringRedisSerializer()
    val valueSerializer: Jackson2JsonRedisSerializer<Any> =
        Jackson2JsonRedisSerializer(Any::class.java)
    val builder: RedisSerializationContextBuilder<String,Any> =
        RedisSerializationContext.newSerializationContext(keySerializer)
    val context: RedisSerializationContext<String, Any> = builder.value(valueSerializer).build()
    return ReactiveRedisTemplate(reactiveRedisConnectionFactory(), context)
}
```
```kotlin
/**
* 데이터 조회
*/
@Service
class ReactiveRedisServiceImpl(private val reactiveRedisTemplate: ReactiveRedisTemplate<String, Any>,
                        private val repository:ReactiveRedisRepository){

    private lateinit var redisValueOps : ReactiveValueOperations<String,Any>

    /**
    * userId값을 키값으로 사용
    * 해당 키에 대한 value가 없으면 DB직접 조회 후, Redis 저장 후 값 반환
    */
    fun getUser(userId: String): Mono<Any> {
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
```


## Caffeine Cache
