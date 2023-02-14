package ru.lyrian.kotlinmultiplatformsandbox.feature.profile.domain

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.lyrian.kotlinmultiplatformsandbox.core.domain.SharedResult
import ru.lyrian.kotlinmultiplatformsandbox.core.domain.asSharedResult

class ProfileInteractor : KoinComponent {
    private val profileRepository: ProfileRepository by inject<ProfileRepository>()

    suspend fun getProfile(): SharedResult<Profile, Throwable> =
        runCatching { profileRepository.getProfile() }.asSharedResult()

    suspend fun saveProfile(profile: Profile): SharedResult<Unit, Throwable> =
        runCatching { profileRepository.saveProfile(profile) }.asSharedResult()
}
