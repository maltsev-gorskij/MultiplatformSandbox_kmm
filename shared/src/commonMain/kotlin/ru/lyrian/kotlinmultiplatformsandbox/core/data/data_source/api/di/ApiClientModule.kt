package ru.lyrian.kotlinmultiplatformsandbox.core.data.data_source.api.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import ru.lyrian.kotlinmultiplatformsandbox.core.build_info.BuildInfo
import ru.lyrian.kotlinmultiplatformsandbox.core.constants.KtorConstants
import ru.lyrian.kotlinmultiplatformsandbox.core.logger.SharedLogger

internal val apiClientModule = module {
    single {
        HttpClient {
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
                    level = LogLevel.ALL
                }
            }

        }
    }
}
