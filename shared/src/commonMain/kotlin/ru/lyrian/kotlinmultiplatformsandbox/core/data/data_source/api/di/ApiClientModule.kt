package ru.lyrian.kotlinmultiplatformsandbox.core.data.data_source.api.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.lyrian.kotlinmultiplatformsandbox.core.build_info.BuildInfo
import ru.lyrian.kotlinmultiplatformsandbox.core.constants.KtorConstants
import ru.lyrian.kotlinmultiplatformsandbox.core.data.data_source.api.ApiClientQualifiers
import ru.lyrian.kotlinmultiplatformsandbox.core.exceptions.KtorExceptions
import ru.lyrian.kotlinmultiplatformsandbox.core.logger.SharedLogger

internal val apiClientModule = module {
    factory<HttpClient>(named(ApiClientQualifiers.COMMON_CLIENT)) {
        HttpClient {
            // Json parsing configs
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        useAlternativeNames = false
                        isLenient = true
                        encodeDefaults = true
                    }
                )
            }

            // Set request timeouts
            install(HttpTimeout) {
                requestTimeoutMillis = KtorConstants.REQUEST_TIMEOUT
                connectTimeoutMillis = KtorConstants.CONNECTION_TIMEOUT
            }

            // Enable Resource style urls
            install(Resources)

            // Response validator to rethrow parsed ktor exceptions
            expectSuccess = true
            HttpResponseValidator {
                handleResponseExceptionWithRequest { throwable: Throwable, _ ->
                    when(throwable) {
                        is RedirectResponseException -> {
                            throw KtorExceptions.RedirectResponse(
                                errorCode = throwable.response.status.value,
                                errorMessage = throwable.response.status.description
                            )
                        }
                        is ClientRequestException -> {
                            throw KtorExceptions.ClientRequest(
                                errorCode = throwable.response.status.value,
                                errorMessage = throwable.response.status.description
                            )
                        }
                        is ServerResponseException -> {
                            throw KtorExceptions.ServerResponse(
                                errorCode = throwable.response.status.value,
                                errorMessage = throwable.response.status.description
                            )
                        }
                        else -> {
                            throw KtorExceptions.General(
                                errorCode = 0,
                                errorMessage = throwable.message.orEmpty()
                            )
                        }
                    }
                }
            }

            // Logging for debug build variant
            if (BuildInfo.isDebug) {
                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            SharedLogger.logDebug(
                                message = message,
                                throwable = null,
                                tag = KtorConstants.LOG_TAG
                            )
                        }
                    }
                    level = LogLevel.BODY
                }
            }
        }
    }

    // Provide ktor client to SpaceX api base url
    factory<HttpClient>(named(ApiClientQualifiers.LAUNCHES_API_CLIENT)) {
        get<HttpClient>(named(ApiClientQualifiers.COMMON_CLIENT)).config {
            install(DefaultRequest) {
                url(KtorConstants.SPACEX_API_BASE_URL)
            }
        }
    }
}
