package ru.lyrian.kotlinmultiplatformsandbox.feature.launches.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.lyrian.kotlinmultiplatformsandbox.AppDatabase
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.dataSource.LaunchesListApi
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.dataSource.SpaceXApiClientProvider
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.repository.LaunchesRepository
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.repository.RocketLaunchMapper
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.domain.LaunchesInteractor

val launchesModule = module {
    // Api
    factoryOf(::SpaceXApiClientProvider)
    factoryOf(::LaunchesListApi)

    // Database
    single { AppDatabase(get()) }
    factory { get<AppDatabase>().appDatabaseQueries }

    // Repository
    factoryOf(::LaunchesRepository)
    factoryOf(::RocketLaunchMapper)

    // Interactor
    factoryOf(::LaunchesInteractor)
}
