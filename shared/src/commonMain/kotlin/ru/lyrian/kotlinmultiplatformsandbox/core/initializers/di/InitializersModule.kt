package ru.lyrian.kotlinmultiplatformsandbox.core.initializers.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.lyrian.kotlinmultiplatformsandbox.core.initializers.AppInitializer
import ru.lyrian.kotlinmultiplatformsandbox.core.initializers.LoggerInitializer

internal val initializersModule = module {
    factoryOf(::LoggerInitializer)
    factoryOf(::AppInitializer)
    includes(firebaseModule)
}