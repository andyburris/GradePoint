package com.andb.apps.aspen

import kotlinx.coroutines.*

actual fun currentTimeMillis(): Long = System.currentTimeMillis()

internal actual fun printThrowable(t: Throwable) {
    t.printStackTrace()
}

actual val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
