package com.andb.apps.aspen

import kotlinx.coroutines.*

expect fun currentTimeMillis(): Long

internal expect fun printThrowable(t: Throwable)

expect val ioDispatcher: CoroutineDispatcher