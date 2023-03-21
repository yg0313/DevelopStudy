package coroutines

import kotlinx.coroutines.*

/**
 * 취소 불가능한 블록.
 * withContext(NonCancellable) 이용하여 취소 불가능한 블록을 만들 수 있다.
 */

suspend fun doWithContextCancellableOneTwoThree() = coroutineScope{
    val job1 = launch {
        withContext(NonCancellable){
            println("launch1: ${Thread.currentThread().name}")
            delay(1000L)
            println("3!")
        }
        //withContext(NonCancellable) 블록 외부의 로직은 취소됨. (delay 800L을 넘겼기때문)
        delay(1000L)
        println("job1: end")
    }

    val job2 = launch {
        withContext(NonCancellable){
            println("launch2: ${Thread.currentThread().name}")
            delay(300L)
            println("1!")
        }
        delay(1000L)
        println("job2: end")
    }

    val job3 = launch {
        withContext(NonCancellable){
            println("launch3: ${Thread.currentThread().name}")
            delay(500L)
            println("2!")
        }
        delay(1000L)
        println("job3: end")
    }

    delay(800L)
    job1.cancel()
    job2.cancel()
    job3.cancel()
    println("4!")
}

fun main() = runBlocking {
    doWithContextCancellableOneTwoThree()
    println("runBlocking ${Thread.currentThread().name}")
    println("5!")
}