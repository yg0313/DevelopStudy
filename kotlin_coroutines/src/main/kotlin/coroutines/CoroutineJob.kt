package coroutines

import kotlinx.coroutines.*

/**
 * Job을 이용한 제어.
 * 코루틴 빌더 launch는 Job객체를 반환하여 이를 통해 종료될 때까지 기다릴 수 있다.
 */

suspend fun doJobOneTwoThree() = coroutineScope{
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

    launch {
        println("launch3: ${Thread.currentThread().name}")
        delay(500L)
        println("2!")
    }

    println("4!")
}

//실행결과
//launch1: main
//3!
//4!
//launch2: main
//1!
//launch3: main
//2!
//runBlocking main
//5!
fun main() = runBlocking {
    doJobOneTwoThree() //coroutineScope는 하위 launch를 비롯한 코루틴 로직실행이 끝날때까지 기다린다. -> coroutineScope가 끝나야 하위 코드 실행.
    println("runBlocking ${Thread.currentThread().name}")
    println("5!")
}