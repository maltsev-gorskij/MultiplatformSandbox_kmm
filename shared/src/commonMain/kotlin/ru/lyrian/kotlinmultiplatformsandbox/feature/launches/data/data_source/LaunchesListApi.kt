package ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.data_source

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import ru.lyrian.kotlinmultiplatformsandbox.core.constants.KtorConstants

internal class LaunchesListApi(private val httpClient: HttpClient) {
    internal suspend fun getAllLaunches(): List<RocketLaunchResponse> = httpClient
            .get(KtorConstants.SPACEX_LAUNCHES_ENDPOINT)
            .body()
}
