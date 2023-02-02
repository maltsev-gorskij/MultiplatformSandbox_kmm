package ru.lyrian.kotlinmultiplatformsandbox.core.data.data_source.api.interceptors_applicator

sealed class ApiError(message: String) : RuntimeException(message) {
    data class BadRequest(override val message: String) : ApiError(message)
    data class Unauthorized(override val message: String) : ApiError(message)
    data class ServerError(override val message: String) : ApiError(message)
    data class NetworkError(val throwable: Throwable, override val message: String) : ApiError(message)
    data class Undefined(val code: Int, override val message: String) : ApiError(message)
}

