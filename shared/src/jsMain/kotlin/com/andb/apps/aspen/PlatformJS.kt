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

actual val ioDispatcher: CoroutineDispatcher = Dispatchers.Default