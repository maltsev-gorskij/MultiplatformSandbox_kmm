package ru.lyrian.kotlinmultiplatformsandbox.feature.profile.data.repository.repository

import kotlinx.coroutines.withContext
import ru.lyrian.kotlinmultiplatformsandbox.core.data.repository.KmmDispatchers
import ru.lyrian.kotlinmultiplatformsandbox.feature.profile.data.data_source.ProfileSettingsDataSource
import ru.lyrian.kotlinmultiplatformsandbox.feature.profile.domain.Profile
import ru.lyrian.kotlinmultiplatformsandbox.feature.profile.domain.ProfileRepository

internal class ProfileRepositoryImpl(
    private val profileSettingsDataSource: ProfileSettingsDataSource,
    private val kmmDispatchers: KmmDispatchers
) : ProfileRepository {
    override suspend fun getProfile(): Profile =
        withContext(kmmDispatchers.io()) {
            Profile(
                userName = profileSettingsDataSource.userName,
                encryptedText = profileSettingsDataSource.testEncryptedText
            )
        }

    override suspend fun saveProfile(profile: Profile) =
        withContext(kmmDispatchers.io()) {
            profileSettingsDataSource.userName = profile.userName
            profileSettingsDataSource.testEncryptedText = profile.encryptedText
        }
}
