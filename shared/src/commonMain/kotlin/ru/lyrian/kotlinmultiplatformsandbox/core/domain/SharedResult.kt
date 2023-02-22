package ru.lyrian.kotlinmultiplatformsandbox.core.domain

import ru.lyrian.kotlinmultiplatformsandbox.core.common.logger.SharedLogger

@Suppress("TooGenericExceptionCaught", "InstanceOfCheckForException")
inline fun <C, R : Any, reified E : Throwable> C.runCatchingResult(block: C.() -> R): SharedResult<R, E> {
    return try {
        SharedResult.Success(block())
    } catch (throwable: Throwable) {
        SharedLogger.logError(
            message = "SharedResult error",
            throwable = throwable,
            tag = "SharedResult"
        )

        SharedResult.Exception(throwable as E)
    }
}

sealed class SharedResult<R : Any, E : Any> {
    class Success<R : Any, E : Any>(val data: R) : SharedResult<R, E>()
    class Exception<R : Any, E : Any>(val exception: E) : SharedResult<R, E>()

    fun isSuccess(): Boolean = this is Success

    fun isException(): Boolean = this is Exception
}
