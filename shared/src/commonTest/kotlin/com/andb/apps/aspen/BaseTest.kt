package com.andb.apps.aspen

import kotlinx.coroutines.CoroutineScope

expect abstract class BaseTest(){
    fun <T> runTest(block: suspend CoroutineScope.() -> T)
}