package ru.lyrian.kotlinmultiplatformsandbox.core.di

import ru.lyrian.kotlinmultiplatformsandbox.feature.firebase_crashlytics_example.di.firebaseIntegrationModule
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.di.launchesModule
import ru.lyrian.kotlinmultiplatformsandbox.feature.profile.di.profileModule

fun featureModules() = listOf(
    launchesModule,
    profileModule,
    firebaseIntegrationModule
)