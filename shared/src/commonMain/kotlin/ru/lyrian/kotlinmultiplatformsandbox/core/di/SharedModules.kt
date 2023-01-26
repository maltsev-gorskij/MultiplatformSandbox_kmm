package ru.lyrian.kotlinmultiplatformsandbox.core.di

import ru.lyrian.kotlinmultiplatformsandbox.core.data.dataSource.api.di.apiClientModule
import ru.lyrian.kotlinmultiplatformsandbox.core.data.dataSource.database.di.DatabaseDriverModule
import ru.lyrian.kotlinmultiplatformsandbox.core.data.dataSource.preferences.di.SettingsModule
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.di.launchesModule
import ru.lyrian.kotlinmultiplatformsandbox.feature.profile.di.profileModule

fun sharedModules() = listOf(
    DatabaseDriverModule().create(),
    SettingsModule().create(),
    apiClientModule,
    launchesModule,
    profileModule
)
