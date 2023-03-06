package ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.repository

import dev.gitlive.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.lyrian.kotlinmultiplatformsandbox.core.common.constants.PaginationConstants
import ru.lyrian.kotlinmultiplatformsandbox.core.data.pagination.PaginationProvider
import ru.lyrian.kotlinmultiplatformsandbox.core.data.pagination.PagingSource
import ru.lyrian.kotlinmultiplatformsandbox.core.data.repository.KmmDispatchers
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.data_source.database.LaunchesDatabaseDataSource
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.data_source.network.LaunchesNetworkDataSource
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.domain.RocketLaunch

@Suppress("UnclearPrecedenceOfBinaryExpression")
internal class LaunchesRepository(
    private val launchesNetworkDataSource: LaunchesNetworkDataSource,
    private val launchesDatabaseDataSource: LaunchesDatabaseDataSource,
    private val kmmDispatchers: KmmDispatchers,
    private val firebaseDatabase: FirebaseDatabase
) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val pagingSource = PagingSource<RocketLaunch>(
        firstPage = PaginationConstants.INITIAL_PAGE,
        coroutineScope = coroutineScope,
        onNextPageRequest = { currentPage, currentItems ->
            withContext(kmmDispatchers.io()) {
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
            withContext(kmmDispatchers.io()) {
                launchesDatabaseDataSource.clearDatabase()
            }
        },
        comparator = { a: RocketLaunch, b: RocketLaunch -> a.id.compareTo(b.id) }
    )

    fun getPaginationProvider(): PaginationProvider<RocketLaunch> = pagingSource.getPaginationProvider()

    suspend fun getLaunchById(launchId: String): RocketLaunch =
        withContext(kmmDispatchers.io()) {
            launchesDatabaseDataSource.getLaunchById(launchId)
        }

    suspend fun addVideoLink() =
        withContext(kmmDispatchers.io()) {
            firebaseDatabase.reference("links").setValue("test")
        }
}