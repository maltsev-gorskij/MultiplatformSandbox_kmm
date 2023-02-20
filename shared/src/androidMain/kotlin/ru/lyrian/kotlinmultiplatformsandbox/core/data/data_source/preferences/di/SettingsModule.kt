package ru.lyrian.kotlinmultiplatformsandbox.core.data.data_source.preferences.di

import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.lyrian.kotlinmultiplatformsandbox.core.common.constants.SettingsConstants

internal actual class SettingsModule {
    internal actual fun create(): Module = module {
        single<Settings>(named(SettingQualifiers.ENCRYPTED)) {
            SharedPreferencesSettings(EncryptedSharedPreferences.create(
                get(),
                SettingsConstants.ENCRYPTED_SETTINGS_FILENAME,
                MasterKey.Builder(get())
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build(),
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            ), false)
        }
        single<Settings>(named(SettingQualifiers.NON_ENCRYPTED)) {
            SharedPreferencesSettings.Factory(get()).create(SettingsConstants.UNENCRYPTED_SETTINGS_FILENAME)
        }
    }
}
