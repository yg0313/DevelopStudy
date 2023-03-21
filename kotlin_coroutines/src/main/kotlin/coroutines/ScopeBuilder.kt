import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main(){
//    coroutineScope()
//    coroutineContext()
//    launchCoroutine()
//    delayCoroutine()
//    threadSleep()
//    multiLaunch()
    outOfBlocking()
}

/**
 * runBlocking{}의 수신객체 -> 코루틴
 * blockingCoroutine -> CoroutineScope의 자식으로, 코틀린 코루틴을 쓰는 모든곳에는
 * 코루틴 스코프가 있다고 생각하면 됨.
 * --> 코루틴의 시작은 코루틴 스코프.
 */
fun coroutineScope() = runBlocking {
    println(this)   // BlockingCoroutine{Active} -> Active: 현재의 코루틴이 활성상태
    println(Thread.currentThread().name)
    println("hello")
}

/**
 * 코루틴 컨텍스트 -> 코루틴 스코프는 코루틴틀 제대로 처리하기 위한 정보인 코루틴 컨텍스트를 가지고 있다.
 *
 */
fun coroutineContext() = runBlocking {
    println(coroutineContext)   // [BlockingCoroutine{Active}@6f1fba17, BlockingEventLoop@185d8b6]
    println(Thread.currentThread().name)
    println("hello")
}

/**
 * launch 코루틴 빌더.
 * 실행 결과: runBlocking: main -> hello -> launch: main ->world!
 * 모두 메인스레드(main)를 사용하기 때문에 runBlocking의 코드들이 메인 스레드를
 * 다 사용 할때까지 launch의 코드 블록이 대기 후 실행.
 */
fun launchCoroutine() = runBlocking {
    launch{
        println("launch: ${Thread.currentThread().name}") //launch: main
        println("world!")
    }
    println("runBlocking: ${Thread.currentThread().name}") //runBlocking: main
    println("hello")
}

/**
 * delay 함수.
 * delay하는 동안 다른 코루틴에게 스레드를 양보하여 다른 코루틴의 코드 실행.
 * 실행결과: runBlocking: main -> launch: main -> world! -> hello
 */
fun delayCoroutine() = runBlocking {
    launch {
        println("launch: ${Thread.currentThread().name}") //launch: main
        println("world!")
    }
    println("runBlocking: ${Thread.currentThread().name}") //runBlocking: main
    delay(500L) //중단점 or suspensionPoint
    println("hello")
}

/**
 * 코루틴 내에서 sleep.
 * 실행결과: runBlocking: main -> hello -> launch: main -> world!
 * 다른 코루틴 스코프에게 스레드를 양보하지않고, 스레드 자체가 쉬었다가 기존 코루틴 스코프 실행.
 */
fun threadSleep() = runBlocking {
    launch{
        println("launch: ${Thread.currentThread().name}") //launch: main
        println("world!")
    }
    println("runBlocking: ${Thread.currentThread().name}") //runBlocking: main
    Thread.sleep(500L) //중단점 or suspensionPoint
    println("hello")
}

/**
 * 한번에 여러 launch
 * 실행 결과: runBlocking: main -> launch1: main -> launch2: main -> 1! -> 2! -> 3!
 */
fun multiLaunch() = runBlocking {
    launch {
        println("launch1: ${Thread.currentThread().name}") //2
        delay(1000L)
        println("3!") //6
    }
    launch {
        println("launch2: ${Thread.currentThread().name}") //3
        println("1!") //4
    }
    println("runBlocking: ${Thread.currentThread().name}") //1
    delay(500L)
    println("2!") //5
}

/**
 * 상위 코루틴은 하위 코루틴을 끝까지 책임진다.
 * runBlocking 안에 launch가 속해 있는데 계층화되어 구조적.
 * runBlocking은 그 속에 포함된 launch가 다 끝나기 전까지 종료되지 않는다.
 * 실행 결과: runBlocking: main -> launch1: main -> launch2: main -> 1! -> 2! -> 3! -> 4!
 */
fun outOfBlocking() {
    runBlocking{ //계층적, 구조적
        launch {
            println("launch1: ${Thread.currentThread().name}")
            delay(1000L)
            println("3!")
        }
        launch {
            println("launch2: ${Thread.currentThread().name}")
            println("1!")
        }
        println("runBlocking: ${Thread.currentThread().name}")
        delay(500L)
        println("2!")
    }
    print("4!")
}