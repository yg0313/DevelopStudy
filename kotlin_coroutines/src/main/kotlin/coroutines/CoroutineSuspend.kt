import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * suspend 함수.
 * delay(), launch 등의 로직내에서 함수로 분리할때 suspend fun 사용.
 */
suspend fun doThree(){
    println("launch1: ${Thread.currentThread().name}")
    delay(1000L)
    println("3!")
}

// delay()를 비롯한 corountine 문법을 쓰지 않는경우, suspend를 붙이지 않아도됨.
fun doOne(){
    println("launch1: ${Thread.currentThread().name}")
    println("1!")
}

suspend fun doTwo(){
    println("runBlocking: ${Thread.currentThread().name}")
    delay(500L)
    println("2!")
}

fun main() = runBlocking {
    launch{
        doThree()
    }
    launch {
        doOne()
    }
    doTwo()
}