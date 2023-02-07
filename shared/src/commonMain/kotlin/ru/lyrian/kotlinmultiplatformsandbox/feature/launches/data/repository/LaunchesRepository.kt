package ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.repository

import dev.icerock.moko.mvvm.ResourceState
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.asFlow
import dev.icerock.moko.mvvm.livedata.readOnly
import dev.icerock.moko.paging.LambdaPagedListDataSource
import dev.icerock.moko.paging.Pagination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.lyrian.kotlinmultiplatformsandbox.core.constants.PaginationConstants
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.data_source.api.LaunchesListApi
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.data_source.api.PagingRequest
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.data_source.api.PagingRequestOptions
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.domain.RocketLaunch

internal class LaunchesRepository(
    private val launchesListApi: LaunchesListApi,
//    private val appDatabaseQueries: AppDatabaseQueries,
    private val rocketLaunchMapper: RocketLaunchMapper
) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private var nextPage = PaginationConstants.INITIAL_PAGE

    private val pagination = Pagination<RocketLaunch>(
        parentScope = coroutineScope,
        dataSource = LambdaPagedListDataSource {
            nextPage++
            getPaginationPage(
                pageNumber = nextPage,
                pageSize = PaginationConstants.PAGE_SIZE,
            )
        },
        comparator = { a, b -> a.id.compareTo(b.id) },
        nextPageListener = { result: Result<List<RocketLaunch>> ->
            // Listening pagination callback for providing
            // ResourceState.Success and ResourceState.Failed to nextPageLoadingState LiveData
            result
                .onSuccess {
                    mutableNextPageLoadingState.value = ResourceState.Success(it)
                }
                .onFailure {
                    mutableNextPageLoadingState.value = ResourceState.Failed(it)
                }
        },
        refreshListener = { result: Result<List<RocketLaunch>> ->
            // Listening pagination callback for providing
            // ResourceState.Success and ResourceState.Failed to refreshLoadingState LiveData
            result
                .onSuccess {
                    mutableRefreshLoadState.value = ResourceState.Success(it)
                }
                .onFailure {
                    mutableRefreshLoadState.value = ResourceState.Failed(it)
                }

        },
        initValue = emptyList()
    )


    // Emits initial page load and errors, new page loads lists.
    val resourceState: LiveData<ResourceState<List<RocketLaunch>, Throwable>> = pagination.state

    // Private mutable and immutable live data nextPageLoadingState to emit correct page load states
    private val mutableNextPageLoadingState =
        MutableLiveData<ResourceState<List<RocketLaunch>, Throwable>>(ResourceState.Empty())
    val nextPageLoadingState: LiveData<ResourceState<List<RocketLaunch>, Throwable>> =
        mutableNextPageLoadingState.readOnly()

    // Private mutable and immutable live data refreshLoadingState to emit correct refresh list states
    private val mutableRefreshLoadState =
        MutableLiveData<ResourceState<List<RocketLaunch>, Throwable>>(ResourceState.Empty())
    val refreshLoadingState: LiveData<ResourceState<List<RocketLaunch>, Throwable>> =
        mutableRefreshLoadState.readOnly()

    init {
        coroutineScope.launch {
            // Listening internal pagination variable for providing
            // ResourceState.Loading to nextPageLoadingState LiveData
            launch {
                pagination
                    .nextPageLoading
                    .asFlow()
                    .collect {
                        if (it) {
                            mutableNextPageLoadingState.value = ResourceState.Loading()
                        }
                    }

            }

            launch {
                // Listening internal pagination variable for providing
                // ResourceState.Loading to refreshLoadingState LiveData
                pagination
                    .refreshLoading
                    .asFlow()
                    .collect {
                        if (it) {
                            mutableRefreshLoadState.value = ResourceState.Loading()
                        }
                    }
            }
        }
    }

    private suspend fun getPaginationPage(pageNumber: Int, pageSize: Int): List<RocketLaunch> =
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

    suspend fun loadFirstPage() {
        pagination.loadFirstPageSuspend()
    }

    suspend fun loadNextPage() {
        pagination.loadNextPageSuspend()
    }

    suspend fun refreshPagination() {
        nextPage = 0
        pagination.refreshSuspend()
    }
}

/*
    internal suspend fun getUpToDateLaunches(): List<RocketLaunch> =
        launchesListApi
            .getAllLaunches()
            .filter { it.staticFireDateUtc != null }
            .map { rocketLaunchMapper(it) }


    internal suspend fun clearDatabase() {
        withContext(Dispatchers.Default) {
            appDatabaseQueries.transaction {
                appDatabaseQueries.removeAllLaunches()
                appDatabaseQueries.removeAllFlickImages()
                appDatabaseQueries.removeAllFailureReasons()
            }
        }
    }

    internal suspend fun getAllCachedLaunches(): List<RocketLaunch> =
        withContext(Dispatchers.Default) {
            appDatabaseQueries.transactionWithResult {
                appDatabaseQueries
                    .getAllLaunches()
                    .executeAsList()
                    .map {
                        rocketLaunchMapper(
                            launchEntity = it,
                            flickrImagesUrls = appDatabaseQueries.getAllFlickrImagesByLaunchId(it.id).executeAsList(),
                            failureReasons = appDatabaseQueries.getAllFailureReasonsById(it.id).executeAsList()
                        )
                    }
            }
        }


    internal suspend fun createLaunches(launches: List<RocketLaunch>) =
        withContext(Dispatchers.Default) {
            appDatabaseQueries.transaction {
                launches.forEach { launch ->
                    if (launch.flickrImagesUrls.isNotEmpty()) {
                        launch.flickrImagesUrls.forEach { imageUrl: String ->
                            appDatabaseQueries.insertFlickrImage(
                                launchId = launch.id,
                                imageUrl = imageUrl
                            )
                        }
                    }

                    if (launch.failureReasons.isNotEmpty()) {
                        launch.failureReasons.forEach { reason: String ->
                            appDatabaseQueries.insertFailureReason(
                                launchId = launch.id,
                                reason = reason
                            )
                        }
                    }

                    appDatabaseQueries.insertLaunch(
                        flightNumber = launch.flightNumber,
                        missionName = launch.missionName,
                        launchYear = launch.launchYear,
                        details = launch.details,
                        launchSuccess = launch.launchSuccess ?: false,
                        launchDateUTC = launch.launchDateUTC,
                        articleUrl = launch.articleUrl,
                        id = launch.id,
                        patchImageUrl = launch.patchImageUrl
                    )
                }
            }
        }

internal suspend fun getLaunchById(launchId: String): RocketLaunch =
    withContext(Dispatchers.Default) {
        appDatabaseQueries.transactionWithResult {
            rocketLaunchMapper(
                launchEntity = appDatabaseQueries
                    .getLaunchById(launchId)
                    .executeAsOne(),
                flickrImagesUrls = appDatabaseQueries
                    .getAllFlickrImagesByLaunchId(launchId)
                    .executeAsList(),
                failureReasons = appDatabaseQueries
                    .getAllFailureReasonsById(launchId)
                    .executeAsList()
            )
        }
    }
*/