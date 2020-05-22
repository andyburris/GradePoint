package com.andb.apps.aspen

import com.andb.apps.aspen.ktor.AspenApi
import com.andb.apps.aspen.ktor.AspenApiImpl
import com.andb.apps.aspen.ktor.DogApi
import com.andb.apps.aspen.ktor.DogApiImpl
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
    single<DogApi> { DogApiImpl() }
    single<AspenApi> { AspenApiImpl() }
}

expect val platformModule: Module