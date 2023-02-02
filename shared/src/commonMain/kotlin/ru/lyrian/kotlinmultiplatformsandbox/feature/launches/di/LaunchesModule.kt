package ru.lyrian.kotlinmultiplatformsandbox.feature.launches.di

import io.ktor.client.HttpClient
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.lyrian.kotlinmultiplatformsandbox.AppDatabase
import ru.lyrian.kotlinmultiplatformsandbox.core.data.data_source.api.di.ApiClientQualifiers
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.data_source.LaunchesListApi
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.repository.LaunchesRepository
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.data.repository.RocketLaunchMapper
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.domain.LaunchesInteractor

val launchesModule = module {
    // Api
    factory { LaunchesListApi(get<HttpClient>(named(ApiClientQualifiers.LAUNCHES_API_CLIENT))) }

    // Database
    single { AppDatabase(get()) }
    factory { get<AppDatabase>().appDatabaseQueries }

    // Repository
    factoryOf(::LaunchesRepository)
    factoryOf(::RocketLaunchMapper)

    // Interactor
    factoryOf(::LaunchesInteractor)
}
