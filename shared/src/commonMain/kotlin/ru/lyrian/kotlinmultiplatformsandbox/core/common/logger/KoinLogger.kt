package ru.lyrian.kotlinmultiplatformsandbox.core.common.logger

import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE
import ru.lyrian.kotlinmultiplatformsandbox.core.common.constants.KoinConstants

internal class KoinLogger(level: Level = Level.DEBUG) : Logger(level) {
    override fun display(level: Level, msg: MESSAGE) {
        when(level) {
            Level.DEBUG -> SharedLogger.logDebug(message = msg, tag = KoinConstants.LOG_TAG)
            Level.INFO -> SharedLogger.logInfo(message = msg, tag = KoinConstants.LOG_TAG)
            Level.WARNING -> SharedLogger.logWarning(message = msg, tag = KoinConstants.LOG_TAG)
            Level.ERROR -> SharedLogger.logError(message = msg, tag = KoinConstants.LOG_TAG)
            else -> SharedLogger.logError(message = msg, tag = KoinConstants.LOG_TAG)
        }
    }
}