package ru.lyrian.kotlinmultiplatformsandbox.core.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

expect class KoinInitializer {
    fun initializeKoin()
}

internal fun initializeSharedGraph(koinApplication: KoinApplication.() -> Unit) {
    startKoin {
        koinApplication()
        modules(sharedModules())
    }
}