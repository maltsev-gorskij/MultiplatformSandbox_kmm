package ru.lyrian.kotlinmultiplatformsandbox.feature.launches.domain

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.repository.LaunchesRepository

class LaunchesInteractor: KoinComponent {
    private val launchesRepository: LaunchesRepository by inject()

    @Throws(Exception::class)
    suspend fun getAllLaunches(forceReload: Boolean = false): List<RocketLaunch> {
        val cachedLaunches = launchesRepository.getAllCachedLaunches()

        return if (cachedLaunches.isNotEmpty() && !forceReload) {
            cachedLaunches
        } else {
            launchesRepository.getUpToDateLaunches().also {
                launchesRepository.clearDatabase()
                launchesRepository.createLaunches(it)
            }
        }
    }

    @Throws(Exception::class)
    suspend fun getLaunchById(launchId: String): RocketLaunch = launchesRepository.getLaunchById(launchId)
}
