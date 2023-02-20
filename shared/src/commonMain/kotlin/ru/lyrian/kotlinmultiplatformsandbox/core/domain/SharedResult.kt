package ru.lyrian.kotlinmultiplatformsandbox.core.domain

sealed class SharedResult<R: Any, E: Any> {
    class Success<R : Any, E : Any>(val data: R) : SharedResult<R, E>()
    class Exception<R : Any, E : Any>(val exception: E) : SharedResult<R, E>()

    fun isSuccess(): Boolean = this is Success

    fun isException(): Boolean = this is Exception
}

internal fun <T : Any> Result<T>.asSharedResult(): SharedResult<T, Throwable> {
    val output = this.getOrNull()
    val exception = this.exceptionOrNull()

    if (output != null) {
        return SharedResult.Success(output)
    } else if (exception != null) {
        return SharedResult.Exception(exception)
    }

    throw SharedResultProduceException("Cannot process Result")
}

class SharedResultProduceException(message: String) : Exception(message)