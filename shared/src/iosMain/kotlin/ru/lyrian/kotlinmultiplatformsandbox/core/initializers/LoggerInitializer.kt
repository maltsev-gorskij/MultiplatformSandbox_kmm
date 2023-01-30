package ru.lyrian.kotlinmultiplatformsandbox.core.initializers

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

internal actual class LoggerInitializer {
    actual fun init() = Napier.base(DebugAntilog())
}