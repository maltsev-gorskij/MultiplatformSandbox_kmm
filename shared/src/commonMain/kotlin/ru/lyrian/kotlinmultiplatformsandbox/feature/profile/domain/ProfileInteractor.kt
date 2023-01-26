package ru.lyrian.kotlinmultiplatformsandbox.feature.profile.domain

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProfileInteractor : KoinComponent {
    private val profileRepository by inject<ProfileRepository>()

    @Throws(Exception::class)
    fun getProfile(): Profile = profileRepository.getProfile()

    @Throws(Exception::class)
    fun saveProfile(profile: Profile) = profileRepository.saveProfile(profile)
}
