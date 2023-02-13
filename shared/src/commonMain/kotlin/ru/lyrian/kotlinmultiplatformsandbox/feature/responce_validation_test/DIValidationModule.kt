package ru.lyrian.kotlinmultiplatformsandbox.feature.responce_validation_test

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.lyrian.kotlinmultiplatformsandbox.core.constants.KtorConstants
import ru.lyrian.kotlinmultiplatformsandbox.core.logger.SharedLogger

val diValidationModule = module {
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
        }
    }

    factory { ValidationInteractor(get<HttpClient>(named(DiValidationModuleQualifiers.DEBUG_HTTP_BIN_CLIENT))) }
}

object DiValidationModuleQualifiers {
    const val DEBUG_HTTP_BIN_CLIENT = "debugHttpBinClient"
}

object DIValidationModuleConstants {
    const val HTTP_BIN_BASE_URL = "https://httpbin.org/"
}