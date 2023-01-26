package ru.lyrian.kotlinmultiplatformsandbox.feature.profile.data.data_source

import com.russhwolf.settings.Settings
import com.russhwolf.settings.string

internal class ProfileSettingsDataSource(
    settings: Settings,
    encryptedSettings: Settings,
) {

    var userName by settings.string(USERNAME_KEY, "")
    var testEncryptedText by encryptedSettings.string(TEST_ENCRYPTED_TEXT, "")

    companion object {
        private const val USERNAME_KEY = "username"
        private const val TEST_ENCRYPTED_TEXT = "test_encrypted_text"
    }
}
