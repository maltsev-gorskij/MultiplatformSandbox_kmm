package ru.lyrian.kotlinmultiplatformsandbox.core.initializers

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AppInitializer: KoinComponent {
    private val loggerInitializer: LoggerInitializer by inject()
    private val crashlyticsInitializer: CrashlyticsInitializer by inject()

    fun init() {
        loggerInitializer.init()
        crashlyticsInitializer.init()
    }
}