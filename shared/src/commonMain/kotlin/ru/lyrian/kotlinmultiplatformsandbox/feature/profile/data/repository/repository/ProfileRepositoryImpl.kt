package ru.lyrian.kotlinmultiplatformsandbox.feature.profile.data.repository.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.lyrian.kotlinmultiplatformsandbox.feature.profile.data.data_source.ProfileSettingsDataSource
import ru.lyrian.kotlinmultiplatformsandbox.feature.profile.domain.Profile
import ru.lyrian.kotlinmultiplatformsandbox.feature.profile.domain.ProfileRepository

internal class ProfileRepositoryImpl(
    private val profileSettingsDataSource: ProfileSettingsDataSource
) : ProfileRepository {
    override suspend fun getProfile(): Profile =
        withContext(Dispatchers.Default) {
            Profile(
                userName = profileSettingsDataSource.userName,
                encryptedText = profileSettingsDataSource.testEncryptedText
            )
        }

    override suspend fun saveProfile(profile: Profile) =
        withContext(Dispatchers.Default) {
            profileSettingsDataSource.userName = profile.userName
            profileSettingsDataSource.testEncryptedText = profile.encryptedText
        }
}
