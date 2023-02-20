package ru.lyrian.kotlinmultiplatformsandbox.core.domain

inline fun <R : Any, T : Any> SharedResult<R, T>.onSuccess(action: (value: R) -> Unit): SharedResult<R, T> {
    if (this is SharedResult.Success<R, T>) action(this.data)

    return this
}

inline fun <R : Any, T : Any> SharedResult<R, T>.onException(action: (exception: T) -> Unit): SharedResult<R, T> {
    if (this is SharedResult.Exception<R, T>) action(this.exception)

    return this
}