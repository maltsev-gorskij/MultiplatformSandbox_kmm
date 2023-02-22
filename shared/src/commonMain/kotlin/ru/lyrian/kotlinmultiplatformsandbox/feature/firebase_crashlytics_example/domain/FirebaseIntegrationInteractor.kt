package ru.lyrian.kotlinmultiplatformsandbox.feature.firebase_crashlytics_example.domain

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.lyrian.kotlinmultiplatformsandbox.core.domain.SharedResult
import ru.lyrian.kotlinmultiplatformsandbox.core.domain.runCatchingResult
import ru.lyrian.kotlinmultiplatformsandbox.feature.firebase_crashlytics_example.data.repository.FirebaseIntegrationRepository

class FirebaseIntegrationInteractor : KoinComponent {
    private val firebaseIntegrationRepository: FirebaseIntegrationRepository by inject()

    fun firebaseCrashlyticsTest(): SharedResult<Nothing, Throwable> =
        runCatchingResult { firebaseIntegrationRepository.generateException() }

    fun produceFatal() = CrashBot().goCrash()
}