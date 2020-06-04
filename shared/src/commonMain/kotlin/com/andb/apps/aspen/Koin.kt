package com.andb.apps.aspen

import com.andb.apps.aspen.data.remote.AspenApi
import com.andb.apps.aspen.data.remote.AspenApiImpl
import com.andb.apps.aspen.data.repository.AspenRepository
import com.netguru.kissme.Kissme
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(platformModule, coreModule)
}

private val coreModule = module {
    single { Kissme("aspenStorage") }
    single { DatabaseHelper(get()) }
    single<AspenApi> { AspenApiImpl() }
    single<AspenRepository> { AspenRepository() }
}

expect val platformModule: Module