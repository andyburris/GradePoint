package com.andb.apps.aspen

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job

expect fun currentTimeMillis(): Long

internal expect fun printThrowable(t: Throwable)

expect fun newIOThread(block: suspend CoroutineScope.() -> Unit): Job
expect fun <T> asyncIO(block: suspend CoroutineScope.() -> T): Deferred<T>
expect suspend fun mainThread(block: suspend CoroutineScope.() -> Unit)
expect suspend fun ioThread(block: suspend CoroutineScope.() -> Unit)