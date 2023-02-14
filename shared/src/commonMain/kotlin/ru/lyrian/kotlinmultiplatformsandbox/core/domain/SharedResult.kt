package ru.lyrian.kotlinmultiplatformsandbox.core.domain

sealed class SharedResult<R: Any, E: Any> {
    class Success<R : Any, E : Any>(val data: R) : SharedResult<R, E>()
    class Exception<R : Any, E : Any>(val exception: E) : SharedResult<R, E>()
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

inline fun <R : Any, T : Any> SharedResult<R, T>.onSuccess(action: (value: R) -> Unit): SharedResult<R, T> {
    if (this is SharedResult.Success<R, T>) action(this.data)

    return this
}

inline fun <R : Any, T : Any> SharedResult<R, T>.onException(action: (exception: T) -> Unit): SharedResult<R, T> {
    if (this is SharedResult.Exception<R, T>) action(this.exception)

    return this
}

class SharedResultProduceException(message: String) : Exception(message)