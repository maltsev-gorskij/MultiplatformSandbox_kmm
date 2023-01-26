package ru.lyrian.kotlinmultiplatformsandbox.feature.profile.data.repository.repository

import ru.lyrian.kotlinmultiplatformsandbox.feature.profile.data.dataSource.ProfileSettingsDataSource
import ru.lyrian.kotlinmultiplatformsandbox.feature.profile.domain.Profile
import ru.lyrian.kotlinmultiplatformsandbox.feature.profile.domain.ProfileRepository

internal class ProfileRepositoryImpl(
    private val profileSettingsDataSource: ProfileSettingsDataSource
) : ProfileRepository {
    @Throws(Exception::class)
    override fun getProfile(): Profile {
        return Profile(
            userName = profileSettingsDataSource.userName,
            encryptedText = profileSettingsDataSource.testEncryptedText
        )
    }

    @Throws(Exception::class)
    override fun saveProfile(profile: Profile) {
        profileSettingsDataSource.userName = profile.userName
        profileSettingsDataSource.testEncryptedText = profile.encryptedText
    }
}
