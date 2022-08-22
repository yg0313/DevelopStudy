package com.study.reactive

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

class ReactiveTypeTest {

    @Test
    @DisplayName("객체로부터 생성 - Flux")
    fun createFluxJust(){
        val fruitFlux = Flux.just("Apple", "Orange", "Grape", "Banana", "StrawBerry")

        StepVerifier.create(fruitFlux)
            .expectNext("Apple")
            .expectNext("Orange")
            .expectNext("Grape")
            .expectNext("Banana")
            .expectNext("StrawBerry")
            .verifyComplete()
    }

    @Test
    @DisplayName("컬렉션으로 부터 생성 - List")
    fun createFluxList(){
        val fruitsList = listOf("Apple", "Orange", "Grape", "Banana", "Strawberry")
        val fruitFlux = Flux.fromIterable(fruitsList)

        StepVerifier.create(fruitFlux)
            .expectNext("Apple")
            .expectNext("Orange")
            .expectNext("Grape")
            .expectNext("Banana")
            .expectNext("Strawberry")
            .verifyComplete()
    }
}