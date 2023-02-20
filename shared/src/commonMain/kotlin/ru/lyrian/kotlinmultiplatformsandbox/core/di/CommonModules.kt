package ru.lyrian.kotlinmultiplatformsandbox.core.di

import ru.lyrian.kotlinmultiplatformsandbox.core.data.data_source.api.di.apiClientModule
import ru.lyrian.kotlinmultiplatformsandbox.core.data.data_source.database.di.DatabaseDriverModule
import ru.lyrian.kotlinmultiplatformsandbox.core.data.data_source.preferences.di.SettingsModule
import ru.lyrian.kotlinmultiplatformsandbox.core.data.repository.di.repositoryModule
import ru.lyrian.kotlinmultiplatformsandbox.core.initializers.di.initializersModule

fun commonModules() = listOf(
    DatabaseDriverModule().create(),
    SettingsModule().create(),
    apiClientModule,
    initializersModule,
    repositoryModule
)
