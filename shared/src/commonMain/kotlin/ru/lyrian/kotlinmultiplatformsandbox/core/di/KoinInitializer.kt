package ru.lyrian.kotlinmultiplatformsandbox.core.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.lyrian.kotlinmultiplatformsandbox.core.build_info.BuildInfo

// Android call
fun initializeKoin(koinApplication: KoinApplication.() -> Unit) {
    startKoin {
        koinApplication()
        modules(sharedModules())
    }
}

// IOS call
fun initializeKoin() = initializeKoin {
    if (BuildInfo.isDebug) printLogger(level = Level.DEBUG)
}
