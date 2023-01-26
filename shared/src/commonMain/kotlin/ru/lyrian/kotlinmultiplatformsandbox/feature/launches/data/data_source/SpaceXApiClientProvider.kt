package ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.data_source

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import ru.lyrian.kotlinmultiplatformsandbox.core.constants.KtorConstants.SPACEX_API_BASE_URL

internal class SpaceXApiClientProvider(httpClient: HttpClient) {
    val client: HttpClient = httpClient.config {
        install(DefaultRequest) {
            url(SPACEX_API_BASE_URL)
        }
    }
}
