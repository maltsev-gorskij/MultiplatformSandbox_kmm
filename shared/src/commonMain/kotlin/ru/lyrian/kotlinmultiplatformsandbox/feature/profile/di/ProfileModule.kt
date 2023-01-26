package ru.lyrian.kotlinmultiplatformsandbox.feature.profile.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.lyrian.kotlinmultiplatformsandbox.core.data.dataSource.preferences.di.SettingQualifiers
import ru.lyrian.kotlinmultiplatformsandbox.feature.profile.data.dataSource.ProfileSettingsDataSource
import ru.lyrian.kotlinmultiplatformsandbox.feature.profile.data.repository.repository.ProfileRepositoryImpl
import ru.lyrian.kotlinmultiplatformsandbox.feature.profile.domain.ProfileInteractor
import ru.lyrian.kotlinmultiplatformsandbox.feature.profile.domain.ProfileRepository

internal val profileModule = module {
    // Data Sources
    factory {
        ProfileSettingsDataSource(
            settings = get(named(SettingQualifiers.NON_ENCRYPTED)),
            encryptedSettings = get(named(SettingQualifiers.ENCRYPTED))
        )
    }

    // Repositories
    factory<ProfileRepository> {
        ProfileRepositoryImpl(get())
    }

    // Interactors
    factoryOf(::ProfileInteractor)
}
