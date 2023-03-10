package ru.lyrian.kotlinmultiplatformsandbox.feature.launches.domain

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.lyrian.kotlinmultiplatformsandbox.core.data.pagination.PaginationProvider
import ru.lyrian.kotlinmultiplatformsandbox.core.domain.SharedResult
import ru.lyrian.kotlinmultiplatformsandbox.core.domain.runCatchingResult
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.repository.LaunchesRepository

class LaunchesInteractor : KoinComponent {
    private val launchesRepository: LaunchesRepository by inject()

    /**
     * Initialize Pagination and fetches the first page.
     * Returns [PaginationProvider] object.
     */
    fun getPaginationProvider() = launchesRepository.getPaginationProvider()

    // Launches Details screen fetching cached launch data
    suspend fun getLaunchById(launchId: String): SharedResult<RocketLaunch, Throwable> =
        runCatchingResult { launchesRepository.getLaunchById(launchId) }

    // TODO -- Example method of Firebase usage. Proof of concept that Firebase is working in KMM
    suspend fun addVideoLink() = launchesRepository.addVideoLink()
}
