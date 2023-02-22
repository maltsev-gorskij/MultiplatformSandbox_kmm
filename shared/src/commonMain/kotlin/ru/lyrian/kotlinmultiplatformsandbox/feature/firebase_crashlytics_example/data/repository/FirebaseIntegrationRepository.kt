package ru.lyrian.kotlinmultiplatformsandbox.feature.firebase_crashlytics_example.data.repository

internal class FirebaseIntegrationRepository {
    @Suppress("TooGenericExceptionThrown")
    fun generateException(): Nothing = throw RuntimeException("test")
}