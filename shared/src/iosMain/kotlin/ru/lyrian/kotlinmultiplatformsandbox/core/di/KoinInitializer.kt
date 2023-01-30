package ru.lyrian.kotlinmultiplatformsandbox.core.di

import org.koin.core.logger.Level
import ru.lyrian.kotlinmultiplatformsandbox.core.build_info.BuildInfo

actual class KoinInitializer {
    actual fun initializeKoin() = initializeSharedGraph {
        if (BuildInfo.isDebug) printLogger(level = Level.DEBUG)
    }
}