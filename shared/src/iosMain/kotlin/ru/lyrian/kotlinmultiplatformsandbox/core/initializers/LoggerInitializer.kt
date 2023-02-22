package ru.lyrian.kotlinmultiplatformsandbox.core.initializers

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import ru.lyrian.kotlinmultiplatformsandbox.core.common.build_info.BuildInfo
import ru.lyrian.kotlinmultiplatformsandbox.core.common.logger.NapierCrashlyticsLogger

internal actual class LoggerInitializer {
    actual fun init() {
        if(BuildInfo.isDebug) {
            Napier.base(antilog = DebugAntilog())
        } else {
            Napier.base(antilog = NapierCrashlyticsLogger())
        }
    }
}