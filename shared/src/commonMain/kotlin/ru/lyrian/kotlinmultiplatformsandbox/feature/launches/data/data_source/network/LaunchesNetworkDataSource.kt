package ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.data_source.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.data_source.network.api.LaunchesListApi
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.data_source.network.api.PagingRequest
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.data_source.network.api.PagingRequestOptions
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.repository.RocketLaunchMapper
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.domain.RocketLaunch

internal class LaunchesNetworkDataSource constructor(
    private val launchesListApi: LaunchesListApi,
    private val rocketLaunchMapper: RocketLaunchMapper
) {
    suspend fun getPaginationPage(pageNumber: Int, pageSize: Int): List<RocketLaunch> =
        withContext(Dispatchers.Default) {
            launchesListApi
                .getLaunchesByPage(
                    pagingRequest = PagingRequest(
                        options = PagingRequestOptions(
                            page = pageNumber,
                            limit = pageSize,
                        )
                    )
                )
                .docs
                .map(rocketLaunchMapper::invoke)
        }
}