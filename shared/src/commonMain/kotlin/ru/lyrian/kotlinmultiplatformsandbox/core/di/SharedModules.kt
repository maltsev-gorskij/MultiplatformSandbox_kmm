package ru.lyrian.kotlinmultiplatformsandbox.core.di

import ru.lyrian.kotlinmultiplatformsandbox.core.data.dataSource.api.di.apiClientModule
import ru.lyrian.kotlinmultiplatformsandbox.core.data.dataSource.database.di.DatabaseDriverModule
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.di.launchesModule

internal fun sharedModules() = listOf(
    DatabaseDriverModule().create(),
    apiClientModule,
    launchesModule
)
