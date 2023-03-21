package coroutines

import kotlinx.coroutines.*

/**
 * 코루틴 스코프.
 * launch{}는 coroutine body 안에서만 사용해야한다.
 * suspend Function 내에서 coroutineBuilder를 사용하기 위해선 coroutineScope를 사용.
 */

/**
 * runBlocking vs coroutineScope.
 * runBlocking은 현재 스레드를 멈추게 만들고 launch와 같은 다른 코루틴 로직을 처리할때까지 기다리지만,
 * coroutineScope는 현재 스레드를 멈추게 하지 않고, 호출한 쪽이 suspend되고 시작되면 다시 활동.
 */

//실행결과: 4! -> launch1: main -> launch2: main -> 1! -> launch3: main -> 2! -> 3! -> runBlocking: main -> 5! -> end
fun main() {
    runBlocking {
        doOneTwoThree()
        println("runBlocking: ${Thread.currentThread().name}")
        println("5!")
    }
    println("end")
}

suspend fun doOneTwoThree() = coroutineScope { //coroutineScope -> coroutineBody 생성, this: 부모 코루틴
    launch{ //this: 자식 코루틴
        println("launch1: ${Thread.currentThread().name}")
        delay(1000L)
        println("3!")
    }
    launch{ //this: 자식 코루틴
        println("launch2: ${Thread.currentThread().name}")
        println("1!")
    }
    launch { //this: 자식 코루틴
        println("launch3: ${Thread.currentThread().name}")
        delay(500L)
        println("2!")
    }
    println("4!")
}