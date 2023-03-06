package ru.lyrian.kotlinmultiplatformsandbox.core.initializers.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.lyrian.kotlinmultiplatformsandbox.core.initializers.CrashlyticsInitializer

actual val crashlyticsModule: Module = module {
    factoryOf(::CrashlyticsInitializer)
}