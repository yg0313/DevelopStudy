package coroutines

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Job에 대해 취소
 */

suspend fun doCancelOneTwoThree() = coroutineScope{
    val job1 = launch {
        println("launch1: ${Thread.currentThread().name}")
        delay(1000L)
        println("3!")
    }

    val job2 = launch {
        println("launch2: ${Thread.currentThread().name}")
        println("1!")
    }

    val job3 = launch {
        println("launch3: ${Thread.currentThread().name}")
        delay(500L)
        println("2!")
    }
    delay(800L)
    job1.cancel()
    job2.cancel()
    job3.cancel()
    println("4!")
}

//실행결과
//launch1: main
//launch2: main
//1!
//launch3: main
//2!
//4!
//runBlocking main
//5!
fun main() = runBlocking {
    doCancelOneTwoThree()
    println("runBlocking ${Thread.currentThread().name}")
    println("5!")
}