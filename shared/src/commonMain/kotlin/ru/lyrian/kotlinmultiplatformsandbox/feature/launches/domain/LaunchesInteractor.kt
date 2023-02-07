package ru.lyrian.kotlinmultiplatformsandbox.feature.launches.domain

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.repository.LaunchesRepository

class LaunchesInteractor: KoinComponent {
    private val launchesRepository: LaunchesRepository by inject()

    /**
     * Initialize Pagination and fetches the first page.
     * Returns PaginationState object.
     * Object contain methods to refresh and load new page and observable LiveData's of current state
     */
    suspend fun initializePagination(): PaginationState {
        launchesRepository.loadFirstPage()

        return PaginationState(
            resourceState = launchesRepository.resourceState,
            nextPageLoadingState = launchesRepository.nextPageLoadingState,
            refreshLoadingState = launchesRepository.refreshLoadingState
        )
    }

    /**
     * Refresh all paginated data from network.
    * */
    suspend fun refreshPagination() = launchesRepository.refreshPagination()

    /**
     * Request next page load.
     * */
    suspend fun loadNextPage() = launchesRepository.loadNextPage()

    // Launches Details screen fetching cached launch data
//    @Throws(Exception::class)
//    suspend fun getLaunchById(launchId: String): RocketLaunch = launchesRepository.getLaunchById(launchId)
}
