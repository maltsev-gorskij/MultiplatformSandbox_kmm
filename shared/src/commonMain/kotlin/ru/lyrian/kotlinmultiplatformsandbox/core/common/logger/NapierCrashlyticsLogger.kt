package ru.lyrian.kotlinmultiplatformsandbox.core.common.logger

import co.touchlab.crashkios.crashlytics.CrashlyticsKotlin
import io.github.aakira.napier.Antilog
import io.github.aakira.napier.LogLevel

class NapierCrashlyticsLogger : Antilog() {
    override fun performLog(
        priority: LogLevel,
        tag: String?,
        throwable: Throwable?,
        message: String?
    ) {
        if (priority < LogLevel.ERROR) return

        throwable?.let {
            CrashlyticsKotlin.sendHandledException(it)
        }
    }
}