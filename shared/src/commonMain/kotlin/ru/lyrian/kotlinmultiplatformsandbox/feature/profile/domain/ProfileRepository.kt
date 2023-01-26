package ru.lyrian.kotlinmultiplatformsandbox.feature.profile.domain

internal interface ProfileRepository {
    fun getProfile(): Profile
    fun saveProfile(profile: Profile)
}
