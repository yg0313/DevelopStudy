package coroutines

import kotlinx.coroutines.*

/**
 * 가벼운 코루틴.
 */
suspend fun doRepeatOneTwoThree() = coroutineScope(){
    val job = launch {
        println("launch1: ${Thread.currentThread().name}")
        delay(1000L)
        println("3!")
    }
    job.join() // suspension point -> 해당 launch block이 끝날때까지 대기, block내의 코드가 전부 호출되면 다음 코드가 실행.

    launch {
        println("launch2: ${Thread.currentThread().name}")
        println("1!")
    }
    
    repeat(10000){
        launch {
            println("launch3: ${Thread.currentThread().name}")
            delay(5000L)
            println("2!")
        }
    }

    println("4!")
}

fun main() = runBlocking {
    doRepeatOneTwoThree()
    println("runBlocking ${Thread.currentThread().name}")
    println("5!")
}