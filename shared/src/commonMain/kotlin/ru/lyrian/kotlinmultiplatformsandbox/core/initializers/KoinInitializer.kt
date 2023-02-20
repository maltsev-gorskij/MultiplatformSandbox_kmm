package ru.lyrian.kotlinmultiplatformsandbox.core.initializers

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.lyrian.kotlinmultiplatformsandbox.core.common.build_info.BuildInfo
import ru.lyrian.kotlinmultiplatformsandbox.core.common.logger.KoinLogger
import ru.lyrian.kotlinmultiplatformsandbox.core.di.commonModules
import ru.lyrian.kotlinmultiplatformsandbox.core.di.featureModules

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