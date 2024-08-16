import kotlinx.coroutines.*

import kotlin.coroutines.EmptyCoroutineContext

fun main() = runBlocking {
    val job = CoroutineScope(EmptyCoroutineContext).launch {
//        launch {
//            delay(500)
            println("ok") // <--
//        }
        launch {
//            delay(500)
            println("ok")
        }
    }
//    delay(100)
//    job.cancelAndJoin()
}
