package com.andb.apps.aspen

expect fun currentTimeMillis(): Long

internal expect fun printThrowable(t: Throwable)