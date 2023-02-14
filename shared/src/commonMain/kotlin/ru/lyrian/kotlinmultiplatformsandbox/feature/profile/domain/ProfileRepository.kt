package ru.lyrian.kotlinmultiplatformsandbox.feature.profile.domain

internal interface ProfileRepository {
    suspend fun getProfile(): Profile
    suspend fun saveProfile(profile: Profile)
}
