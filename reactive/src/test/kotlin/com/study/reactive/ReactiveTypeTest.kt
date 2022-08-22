package com.study.reactive

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import reactor.util.function.Tuple2
import java.time.Duration

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

    @Test
    @DisplayName("Flux 데이터 생성하기 - 증가하는 값 만들기")
    fun createFluxRange(){
        val rangeFlux = Flux.range(1,5)

        StepVerifier.create(rangeFlux)
            .expectNext(1)
            .expectNext(2)
            .expectNext(3)
            .expectNext(4)
            .expectNext(5)
            .verifyComplete()
    }

    @Test
    @DisplayName("리액티브 타입 결합 - mergeWith()")
    fun mergeFlux(){
        val characterFlux = Flux.just("Garfield", "Kojak", "Barbossa")
            .delayElements(Duration.ofMillis(500))
        val foodFlux = Flux.just("Lasagna", "Lollipops", "Apples")
            .delaySubscription(Duration.ofMillis(250))
            .delayElements(Duration.ofMillis(500))

        val mergedFlux = characterFlux.mergeWith(foodFlux)
        StepVerifier.create(mergedFlux)
            .expectNext("Garfield")
            .expectNext("Lasagna")
            .expectNext("Kojak")
            .expectNext("Lollipops")
            .expectNext("Barbossa")
            .expectNext("Apples")
            .verifyComplete()
    }

    @Test
    @DisplayName("리액티브 타입 결합 - zip()")
    fun zipFlux(){
        val characterFlux = Flux.just("Garfield", "Kojak", "Barbossa")
        val foodFlux = Flux.just("Lasagna", "Lollipops", "Apples")

        val zippedFlux : Flux<Tuple2<String,String>> = Flux.zip(characterFlux, foodFlux)

        StepVerifier.create(zippedFlux)
            .expectNextMatches{
                it.t1 == "Garfield" && it.t2 == "Lasagna"
            }.expectNextMatches{
                it.t1 == "Kojak" && it.t2 == "Lollipops"
            }.expectNextMatches{
                it.t1 == "Barbossa" && it.t2 == "Apples"
            }.verifyComplete()

    }

    @Test
    @DisplayName("리액티브 타입 결합 - zip(), 다른 타입 반환")
    fun zipFluxToString(){
        val characterFlux = Flux.just("Garfield", "Kojak", "Barbossa")
        val foodFlux = Flux.just("Lasagna", "Lollipops", "Apples")

        val zippedFlux : Flux<String> = Flux.zip(characterFlux, foodFlux)
            .flatMap {
                Mono.just("${it.t1} eats ${it.t2}")
            }

        zippedFlux.subscribe {
            println("it = ${it}")
        }

        StepVerifier.create(zippedFlux)
            .expectNext("Garfield eats Lasagna")
            .expectNext("Kojak eats Lollipops")
            .expectNext("Barbossa eats Apples")
            .verifyComplete()
    }
}