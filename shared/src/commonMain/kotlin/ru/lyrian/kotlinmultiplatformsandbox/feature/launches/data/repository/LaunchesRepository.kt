package ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.lyrian.kotlinmultiplatformsandbox.core.constants.PaginationConstants
import ru.lyrian.kotlinmultiplatformsandbox.core.data.pagination.PaginationProvider
import ru.lyrian.kotlinmultiplatformsandbox.core.data.pagination.PagingSource
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.data_source.database.LaunchesDatabaseDataSource
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.data_source.network.LaunchesNetworkDataSource
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.domain.RocketLaunch

@Suppress("UnclearPrecedenceOfBinaryExpression")
internal class LaunchesRepository(
    private val launchesNetworkDataSource: LaunchesNetworkDataSource,
    private val launchesDatabaseDataSource: LaunchesDatabaseDataSource
) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val pagingSource = PagingSource<RocketLaunch>(
        firstPage = PaginationConstants.INITIAL_PAGE,
        coroutineScope = coroutineScope,
        onNextPageRequest = { currentPage, currentItems ->
            withContext(Dispatchers.Default) {
                val dbItemsCount = launchesDatabaseDataSource.getLaunchesCount()
                val currentItemsSize = currentItems?.size ?: 0
                val condition = dbItemsCount < currentItemsSize + 1

                if (condition) {
                    val pageFromApi = launchesNetworkDataSource.getPaginationPage(
                        pageNumber = currentPage,
                        pageSize = PaginationConstants.PAGE_SIZE,
                    )
                    launchesDatabaseDataSource.insertLaunches(pageFromApi)
                }

                val result = launchesDatabaseDataSource.getLaunchesByPageSizeAndOffset(
                    pageSize = PaginationConstants.PAGE_SIZE,
                    offset = (currentPage - 1) * PaginationConstants.PAGE_SIZE
                )

                result
            }
        },
        onRefreshRequest = {
            withContext(Dispatchers.Default) {
                launchesDatabaseDataSource.clearDatabase()
            }
        },
        comparator = { a: RocketLaunch, b: RocketLaunch -> a.id.compareTo(b.id) }
    )

    fun getPaginationProvider(): PaginationProvider<RocketLaunch> = pagingSource.getPaginationProvider()

    suspend fun getLaunchById(launchId: String): RocketLaunch =
        withContext(Dispatchers.Default) {
            launchesDatabaseDataSource.getLaunchById(launchId)
        }
}