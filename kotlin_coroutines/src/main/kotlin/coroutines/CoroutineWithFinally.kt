package coroutines

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * finally를 같이 사용.
 * suspend 함수들은 JobCancellationException 을 발생하기 때문에 표준 try catch finally로 대응이 가능하다.
 */

suspend fun doWithFinallyOneTwoThree() = coroutineScope{
    val job1 = launch {
        try{
            println("launch1: ${Thread.currentThread().name}")
            delay(1000L)
            println("3!")
        }finally {
            println("job1 is finishing!")
        }
    }

    val job2 = launch {
        try{
            println("launch2: ${Thread.currentThread().name}")
            delay(1000L)
            println("1!")
        }finally {
            println("job2 is finishing!")
        }
    }

    val job3 = launch {
        try{
            println("launch3: ${Thread.currentThread().name}")
            delay(500L)
            println("2!")
        }finally {
            println("job3 is finishing!")
        }
    }

    delay(800L)
    job1.cancel()
    job2.cancel()
    job3.cancel()
    println("4!")
}

fun main() = runBlocking {
    doWithFinallyOneTwoThree()
    println("runBlocking ${Thread.currentThread().name}")
    println("5!")
}