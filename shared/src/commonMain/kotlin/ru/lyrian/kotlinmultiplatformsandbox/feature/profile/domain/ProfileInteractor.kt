package ru.lyrian.kotlinmultiplatformsandbox.feature.profile.domain

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.lyrian.kotlinmultiplatformsandbox.core.domain.SharedResult
import ru.lyrian.kotlinmultiplatformsandbox.core.domain.runCatchingResult

class ProfileInteractor : KoinComponent {
    private val profileRepository: ProfileRepository by inject()

    suspend fun getProfile(): SharedResult<Profile, Throwable> =
        runCatchingResult { profileRepository.getProfile() }

    suspend fun saveProfile(profile: Profile): SharedResult<Unit, Throwable> =
        runCatchingResult { profileRepository.saveProfile(profile) }
}
