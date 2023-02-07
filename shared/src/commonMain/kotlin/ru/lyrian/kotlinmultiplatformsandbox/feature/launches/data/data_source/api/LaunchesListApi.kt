package ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.data_source.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import ru.lyrian.kotlinmultiplatformsandbox.core.constants.KtorConstants

internal class LaunchesListApi(private val httpClient: HttpClient) {
    internal suspend fun getAllLaunches(): List<RocketLaunchResponse> = httpClient
            .get(KtorConstants.SPACEX_LAUNCHES_ENDPOINT)
            .body()

    internal suspend fun getLaunchesByPage(pagingRequest: PagingRequest): RocketLaunchesPagingResponse {
        val response = httpClient
            .post(KtorConstants.SPACEX_LAUNCHES_PAGING_ENDPOINT) {
                setBody(pagingRequest)
            }
        return response.body()
    }
}
