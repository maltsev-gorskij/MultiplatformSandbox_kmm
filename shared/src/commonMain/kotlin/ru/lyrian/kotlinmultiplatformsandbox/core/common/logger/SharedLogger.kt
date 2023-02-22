package ru.lyrian.kotlinmultiplatformsandbox.core.common.logger

import io.github.aakira.napier.LogLevel
import io.github.aakira.napier.Napier
import ru.lyrian.kotlinmultiplatformsandbox.core.common.constants.NapierConstants

object SharedLogger {
    fun logVerbose(
        message: String,
        throwable: Throwable? = null,
        tag: String? = null
    ) = log(LogLevel.VERBOSE, message, throwable, tag)

    fun logDebug(
        message: String,
        throwable: Throwable? = null,
        tag: String? = null
    ) = log(LogLevel.DEBUG, message, throwable, tag)

    fun logInfo(
        message: String,
        throwable: Throwable? = null,
        tag: String? = null
    ) = log(LogLevel.INFO, message, throwable, tag)

    fun logWarning(
        message: String,
        throwable: Throwable? = null,
        tag: String? = null
    ) = log(LogLevel.WARNING, message, throwable, tag)

    fun logError(
        message: String,
        throwable: Throwable? = null,
        tag: String? = null
    ) = log(LogLevel.ERROR, message, throwable, tag)

    private fun log(
        priority: LogLevel,
        message: String,
        throwable: Throwable? = null,
        tag: String? = null
    ) {
        Napier.log(
            priority = priority,
            tag = NapierConstants.NAPIER_LOG_PREFIX + tag,
            throwable = throwable,
            message = message
        )
    }
}