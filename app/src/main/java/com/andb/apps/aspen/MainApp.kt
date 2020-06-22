package com.andb.apps.aspen

import android.app.Application
import android.content.Context
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            modules(
                module {
                    single<Context> { this@MainApp }
                    viewModel { MainActivityViewModel() }
                }
            )
        }
    }
}