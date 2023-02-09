package ru.lyrian.kotlinmultiplatformsandbox.feature.launches.domain

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.lyrian.kotlinmultiplatformsandbox.core.data.pagination.PaginationState
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.repository.LaunchesRepository

class LaunchesInteractor: KoinComponent {
    private val launchesRepository: LaunchesRepository by inject()

    /**
     * Initialize Pagination and fetches the first page.
     * Returns [PaginationState] object.
     */
    suspend fun initializePagination(): PaginationState<RocketLaunch> = launchesRepository.providePaginationState()

    // Launches Details screen fetching cached launch data
    @Throws(Exception::class)
    suspend fun getLaunchById(launchId: String): RocketLaunch = launchesRepository.getLaunchById(launchId)
}
