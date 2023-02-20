package ru.lyrian.kotlinmultiplatformsandbox.core.data.data_source.preferences.di

import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.KeychainSettings
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.lyrian.kotlinmultiplatformsandbox.core.common.constants.SettingsConstants

internal actual class SettingsModule {
    @OptIn(ExperimentalSettingsImplementation::class)
    internal actual fun create(): Module = module {
        single<Settings>(named(SettingQualifiers.ENCRYPTED)) {
            KeychainSettings(service = SettingsConstants.ENCRYPTED_SETTINGS_FILENAME)
        }

        single<Settings>(named(SettingQualifiers.NON_ENCRYPTED)) {
            NSUserDefaultsSettings.Factory().create(SettingsConstants.ENCRYPTED_SETTINGS_FILENAME)
        }
    }
}
