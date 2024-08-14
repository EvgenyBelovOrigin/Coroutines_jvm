package ru.netokogy.pusher

import kotlinx.coroutines.*
import java.util.concurrent.Executors
import kotlin.coroutines.EmptyCoroutineContext

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
//1111111111111111111111111111111111111111111111111111111111111
    CoroutineScope(EmptyCoroutineContext).launch {
        println(Thread.currentThread().name)
    }
    Thread.sleep(1000L)

println()
//2222222222222222222222222222222222222222222222222222222222
    val custom = Executors.newFixedThreadPool(64).asCoroutineDispatcher()
    with (CoroutineScope(EmptyCoroutineContext)) {
        launch(Dispatchers.Default) {
            println(Thread.currentThread().name)
        }
        launch(Dispatchers.IO) {
            println(Thread.currentThread().name)
        }
        launch(custom) {
            println(Thread.currentThread().name)
        }
    }
    Thread.sleep(1000)
    custom.close()

}