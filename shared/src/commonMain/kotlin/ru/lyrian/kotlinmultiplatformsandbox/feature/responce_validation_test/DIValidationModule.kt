package ru.lyrian.kotlinmultiplatformsandbox.feature.responce_validation_test

import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.lyrian.kotlinmultiplatformsandbox.core.constants.KtorConstants
import ru.lyrian.kotlinmultiplatformsandbox.core.logger.SharedLogger

internal val diValidationModule = module {
    factory<HttpClient>(named(DiValidationModuleQualifiers.DEBUG_HTTP_BIN_CLIENT)) {
        HttpClient {
            expectSuccess = true

            install(Resources)

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

            install(DefaultRequest) {
                url(DIValidationModuleConstants.HTTP_BIN_BASE_URL)
            }

            HttpResponseValidator {
                handleResponseExceptionWithRequest { throwable: Throwable, _ ->
                    when(throwable) {
                        is RedirectResponseException -> {
                            throw KtorErrors.RedirectResponse(
                                errorCode = throwable.response.status.value,
                                errorMessage = throwable.response.status.description
                            )
                        }
                        is ClientRequestException -> {
                            throw KtorErrors.ClientRequest(
                                errorCode = throwable.response.status.value,
                                errorMessage = throwable.response.status.description
                            )
                        }
                        is ServerResponseException -> {
                            throw KtorErrors.ServerResponse(
                                errorCode = throwable.response.status.value,
                                errorMessage = throwable.response.status.description
                            )
                        }
                        else -> {
                            throw KtorErrors.GeneralNetworkError(
                                errorCode = 0,
                                errorMessage = throwable.message ?: ""
                            )
                        }
                    }
                }
            }
        }
    }

    factory { ValidationInteractor(get<HttpClient>(named(DiValidationModuleQualifiers.DEBUG_HTTP_BIN_CLIENT))) }
}

internal object DiValidationModuleQualifiers {
    const val DEBUG_HTTP_BIN_CLIENT = "debugHttpBinClient"
}

internal object DIValidationModuleConstants {
    const val HTTP_BIN_BASE_URL = "https://httpbin.org/"
}

sealed class KtorErrors(val errorCode: Int, val errorMessage: String): RuntimeException(errorMessage) {
    class RedirectResponse(errorCode: Int, errorMessage: String) : KtorErrors(errorCode, errorMessage)
    class ClientRequest(errorCode: Int, errorMessage: String) : KtorErrors(errorCode, errorMessage)
    class ServerResponse(errorCode: Int, errorMessage: String) : KtorErrors(errorCode, errorMessage)
    class GeneralNetworkError(errorCode: Int, errorMessage: String) : KtorErrors(errorCode, errorMessage)
}