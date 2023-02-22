package ru.lyrian.kotlinmultiplatformsandbox.feature.firebase_crashlytics_example.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.lyrian.kotlinmultiplatformsandbox.feature.firebase_crashlytics_example.data.repository.FirebaseIntegrationRepository
import ru.lyrian.kotlinmultiplatformsandbox.feature.firebase_crashlytics_example.domain.FirebaseIntegrationInteractor

val firebaseIntegrationModule = module {
    factoryOf(::FirebaseIntegrationRepository)
    factoryOf(::FirebaseIntegrationInteractor)
}