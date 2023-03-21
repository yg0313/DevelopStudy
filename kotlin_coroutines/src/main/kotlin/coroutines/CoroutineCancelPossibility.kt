package coroutines

import kotlinx.coroutines.*

/**
 * cancel 가능한 코루틴.
 * isActive를 호출하여 해당 코루틴이 여전히 활성화된지 확인할 수 있다.
 */
suspend fun doCountCancelPossibility() = coroutineScope {
    val job1 = launch(Dispatchers.Default) {
        var i = 1
        var nextTime = System.currentTimeMillis() + 100L

        while (i <= 10 && isActive) {
            val currentTime = System.currentTimeMillis()
            if (currentTime > nextTime) {
                println(i)
                nextTime = currentTime + 100L
                i++
            }
        }
    }

    delay(200L)
    job1.cancelAndJoin() //cancel이 호출되는 순간 코루틴의 active가 비활성상태로 변경.
    println("doCount Done!")
}

fun main() = runBlocking {
    doCountCancelPossibility()
}