package ru.lyrian.kotlinmultiplatformsandbox.core.initializers.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.lyrian.kotlinmultiplatformsandbox.core.initializers.AppInitializer
import ru.lyrian.kotlinmultiplatformsandbox.core.initializers.LoggerInitializer

internal val initializersModule = module {
    singleOf(::LoggerInitializer)
    singleOf(::AppInitializer)
}