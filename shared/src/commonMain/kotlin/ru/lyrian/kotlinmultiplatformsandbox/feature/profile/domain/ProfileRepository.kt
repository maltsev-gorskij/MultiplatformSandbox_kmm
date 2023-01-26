package ru.lyrian.kotlinmultiplatformsandbox.feature.profile.domain

internal interface ProfileRepository {
    @Throws(Exception::class)
    fun getProfile(): Profile

    @Throws(Exception::class)
    fun saveProfile(profile: Profile)
}
