package ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.dataSource

import io.ktor.client.call.body
import io.ktor.client.request.get
import ru.lyrian.kotlinmultiplatformsandbox.core.constants.KtorConstants

internal class LaunchesListApi(spaceXApiClientProvider: SpaceXApiClientProvider) {
    private val client = spaceXApiClientProvider.client

    internal suspend fun getAllLaunches(): List<RocketLaunchResponse> = client
            .get(KtorConstants.SPACEX_LAUNCHES_ENDPOINT)
            .body()
}
