package ru.lyrian.kotlinmultiplatformsandbox.core.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.lyrian.kotlinmultiplatformsandbox.core.build_info.BuildInfo

expect class KoinInitializer {
    fun initializeKoin()
}

internal fun initializeSharedGraph(koinApplication: KoinApplication.() -> Unit) {
    startKoin {
        koinApplication()
        if (BuildInfo.isDebug) logger(logger = KoinLogger(level = Level.DEBUG))
        modules(commonModules() + featureModules())
    }
}