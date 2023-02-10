package ru.lyrian.kotlinmultiplatformsandbox.core.data.data_source.api.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
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
import ru.lyrian.kotlinmultiplatformsandbox.core.data.data_source.api.interceptors_applicator.ApiErrorMapperApplicator
import ru.lyrian.kotlinmultiplatformsandbox.core.logger.SharedLogger

internal val apiClientModule = module {
    factory<HttpClient>(named(ApiClientQualifiers.COMMON_CLIENT)) {
        HttpClient {
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

            install(HttpTimeout) {
                requestTimeoutMillis = KtorConstants.REQUEST_TIMEOUT
                connectTimeoutMillis = KtorConstants.CONNECTION_TIMEOUT
            }

            install(Resources)

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

    factory<ApiErrorMapperApplicator> {
        ApiErrorMapperApplicator()
    }

    factory<HttpClient>(named(ApiClientQualifiers.LAUNCHES_API_CLIENT)) {
        val httpClient = get<HttpClient>(named(ApiClientQualifiers.COMMON_CLIENT)).config {
            install(DefaultRequest) {
                url(KtorConstants.SPACEX_API_BASE_URL)
            }
        }

        get<ApiErrorMapperApplicator>()(httpClient)
    }
}
