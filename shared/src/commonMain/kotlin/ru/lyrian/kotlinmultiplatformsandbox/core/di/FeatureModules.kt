package ru.lyrian.kotlinmultiplatformsandbox.core.di

import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.di.launchesModule
import ru.lyrian.kotlinmultiplatformsandbox.feature.profile.di.profileModule
import ru.lyrian.kotlinmultiplatformsandbox.feature.responce_validation_test.diValidationModule

fun featureModules() = listOf(
    launchesModule,
    profileModule,
    diValidationModule
)