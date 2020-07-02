package com.andb.apps.aspen

import kotlinx.coroutines.*
import kotlin.js.Date
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds

@ExperimentalTime
actual fun currentTimeMillis() = Date.now().milliseconds.toLongMilliseconds()
internal actual fun printThrowable(t: Throwable) {
    console.error(t.message)
}

actual fun newIOThread(block: suspend CoroutineScope.() -> Unit): Job = CoroutineScope(Dispatchers.Default).launch(block = block)
actual fun <T> asyncIO(block: suspend CoroutineScope.() -> T): Deferred<T> = CoroutineScope(Dispatchers.Default).async(block = block)
actual suspend fun mainThread(block: suspend CoroutineScope.() -> Unit) {
    withContext(Dispatchers.Main, block)
}
actual suspend fun ioThread(block: suspend CoroutineScope.() -> Unit) {
    withContext(Dispatchers.Default, block)
}