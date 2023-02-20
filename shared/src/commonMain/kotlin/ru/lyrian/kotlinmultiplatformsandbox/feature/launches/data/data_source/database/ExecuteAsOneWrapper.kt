package ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.data_source.database

import ru.lyrian.kotlinmultiplatformsandbox.core.common.exceptions.DatabaseExceptions

@Suppress("TooGenericExceptionCaught")
class ExecuteAsOneWrapper {
    operator fun <T> invoke(query: () -> T): T = try {
        query()
    } catch (exception: Exception) {
        throw when (exception) {
            is IllegalStateException -> DatabaseExceptions.MultipleResults(
                errorMessage = exception.message.orEmpty(),
                stackTrace = exception.stackTraceToString()
            )
            is NullPointerException -> DatabaseExceptions.NoResult(
                errorMessage = exception.message.orEmpty(),
                stackTrace = exception.stackTraceToString()
            )
            else -> DatabaseExceptions.General(
                errorMessage = exception.message.orEmpty(),
                stackTrace = exception.stackTraceToString()
            )
        }
    }
}
