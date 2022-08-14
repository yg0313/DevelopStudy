package com.study.reactive

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Mono

@SpringBootTest
class ReactiveApplicationTests {

	@Test
    @DisplayName("명령형 프로그래밍")
	fun imperativeCodeTest() {
        val name = "Craig"
        val capitalName = name.uppercase()
        val greeting = "Hello, $capitalName!"
        println(greeting)
	}

    /**
     * 데이터가 전달되는 파이프라인 구성.
     * 파이프라인의 각 단계에서는 어떻게 하든 데이터가 변경.
     * 각 오퍼레이션은 같은 스레드로 실행되거나 다른 스레드로 실행될 수 있다.
     */
    @Test
    @DisplayName("리액티브 프로그래밍")
    fun reactiveCodeTest(){
        Mono.just("Craig")
            .map{
                it.uppercase()
            }.map {
                "Hello, $it!"
            }.subscribe(System.out::println)
    }

}
