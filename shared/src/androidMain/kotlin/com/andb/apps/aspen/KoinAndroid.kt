package com.andb.apps.aspen

import android.content.Context
import android.content.SharedPreferences
import com.andb.apps.aspen.db.KampstarterDb
import com.netguru.kissme.Kissme
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            KampstarterDb.Schema,
            get(),
            "KampStarterDb"
        )
    }

    single<Kissme> { Kissme("aspenStorage") }
    single<Storage> { StorageAndroid(get()) }
    single<DatabaseHelper> { DatabaseHelperImpl(get()) }
}