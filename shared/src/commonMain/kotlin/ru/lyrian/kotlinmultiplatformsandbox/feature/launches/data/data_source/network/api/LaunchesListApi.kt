package ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.data_source.network.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody
import ru.lyrian.kotlinmultiplatformsandbox.core.data.data_source.api.Launches

internal class LaunchesListApi(private val httpClient: HttpClient) {
    internal suspend fun getLaunchesByPage(pagingRequest: PagingRequest): RocketLaunchesPagingResponse =
        httpClient
            .post(Launches.Query()) {
                setBody(pagingRequest)
            }
            .body()
}
