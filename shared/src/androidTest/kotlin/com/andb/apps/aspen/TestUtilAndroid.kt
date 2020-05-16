package com.andb.apps.aspen

import android.app.Application
import com.andb.apps.aspen.db.KampstarterDb
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import androidx.test.core.app.ApplicationProvider

internal actual fun testDbConnection(): SqlDriver {
    val app = ApplicationProvider.getApplicationContext<Application>()
    return AndroidSqliteDriver(KampstarterDb.Schema, app, "droidcondb")
}