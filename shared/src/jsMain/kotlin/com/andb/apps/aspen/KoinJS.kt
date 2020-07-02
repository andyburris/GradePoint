package com.andb.apps.aspen

import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<Storage> { StorageJS() }
}