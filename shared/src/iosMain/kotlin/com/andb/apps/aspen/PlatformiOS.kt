package com.andb.apps.aspen

import kotlinx.coroutines.*
import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

actual fun currentTimeMillis(): Long = (NSDate().timeIntervalSince1970 * 1000).toLong()

internal actual fun printThrowable(t: Throwable) {
    t.printStackTrace()
}

actual fun newIOThread(block: suspend CoroutineScope.() -> Unit): Job {
    return CoroutineScope(Dispatchers.Default).launch(block = block)
}

actual fun <T> asyncIO(block: suspend CoroutineScope.() -> T): Deferred<T> {
    return CoroutineScope(Dispatchers.Default).async(block = block)
}

actual suspend fun mainThread(block: suspend CoroutineScope.() -> Unit) {
    withContext(Dispatchers.Main, block)
}

actual suspend fun ioThread(block: suspend CoroutineScope.() -> Unit) {
    withContext(Dispatchers.Default, block)
}
