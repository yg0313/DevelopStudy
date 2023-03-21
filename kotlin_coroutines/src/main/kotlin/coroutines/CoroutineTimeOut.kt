package coroutines

import kotlinx.coroutines.*

/**
 * 타임 아웃 & withTimeoutOrNull
 * 일정 시간이 끝난 후에 종료하고자 할때 withTimeout을 이용.
 * withTimeoutOrNull을 이용하여 타임 아웃일때 null을 반환 (try-catch 생략 가능)
 */
suspend fun doCountTimeOut() = coroutineScope {
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
}

fun main() = runBlocking {
    val result = withTimeoutOrNull(1500L){
        doCountTimeOut()
        true
    } ?: false //엘비스 연산자 -> ?: null이 있으면 우항의 false 반환.
    println(result)
}