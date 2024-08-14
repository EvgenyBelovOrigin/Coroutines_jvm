package ru.netokogy.pusher

import kotlinx.coroutines.runBlocking

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
runBlocking {
   println(Thread.currentThread().name)
}
   Thread.sleep(1000)
}