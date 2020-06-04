package com.andb.apps.aspen

import com.andb.apps.aspen.data.remote.AspenApi
import com.russhwolf.settings.Settings
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.withTimeout
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

fun appStart(helper: DatabaseHelper, settings: Settings, aspenApi: AspenApi) {
    val coreModule = module {
        single { helper }
        single { settings }
        single { aspenApi }
    }

    startKoin { modules(coreModule) }

}

fun appEnd() {
    stopKoin()
}

//Await with a timeout
suspend fun <T> Deferred<T>.await(timeoutMillis: Long) =
    withTimeout(timeoutMillis) { await() }

internal expect fun testDbConnection(): SqlDriver

