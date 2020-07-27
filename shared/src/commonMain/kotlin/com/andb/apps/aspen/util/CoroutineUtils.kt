package com.andb.apps.aspen.util

import com.andb.apps.aspen.ioDispatcher
import kotlinx.coroutines.*

fun newIOThread(block: suspend CoroutineScope.() -> Unit): Job {
    return CoroutineScope(ioDispatcher).launch(block = block)
}

fun <T> asyncIO(block: suspend CoroutineScope.() -> T): Deferred<T> {
    return CoroutineScope(ioDispatcher).async(block = block)
}

suspend fun mainThread(block: suspend CoroutineScope.() -> Unit) {
    withContext(Dispatchers.Main, block)
}

suspend fun ioThread(block: suspend CoroutineScope.() -> Unit) {
    withContext(ioDispatcher, block)
}