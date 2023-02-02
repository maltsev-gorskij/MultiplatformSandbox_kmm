package ru.lyrian.kotlinmultiplatformsandbox.core.data.data_source.api.interceptors_applicator

import io.ktor.client.HttpClient
import io.ktor.client.call.HttpClientCall
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.plugin
import io.ktor.http.isSuccess
import io.ktor.utils.io.errors.IOException

/**
 * Must be applied at the end of httpClient initialization chain.
 * */
class ApiErrorMapperApplicator {
    operator fun invoke(httpClient: HttpClient): HttpClient {
        httpClient.plugin(HttpSend).intercept { request ->
            try {
                val httpClientCall: HttpClientCall = execute(request)
                val returnCode = httpClientCall.response.status.value
                val responseDescription = httpClientCall.response.status.description

                if (httpClientCall.response.status.isSuccess()) return@intercept httpClientCall

                throw when (returnCode) {
                    BAD_REQUEST_CODE -> ApiError.BadRequest(responseDescription)
                    UNAUTHORIZED_CODE -> ApiError.Unauthorized(responseDescription)
                    SERVER_ERROR_CODE -> ApiError.ServerError(responseDescription)
                    else -> ApiError.Undefined(returnCode, responseDescription)
                }
            } catch (ioException: IOException) {
                throw ApiError.NetworkError(ioException, ioException.toString())
            }
        }

        return httpClient
    }

    companion object {
        const val BAD_REQUEST_CODE = 400
        const val UNAUTHORIZED_CODE = 401
        const val SERVER_ERROR_CODE = 500
    }
}
