package ru.lyrian.kotlinmultiplatformsandbox.core.initializers

import org.koin.core.KoinApplication

actual class KoinInitializer(private val koinApplication: KoinApplication.() -> Unit) {
    actual fun initializeKoin() = initializeSharedGraph(koinApplication = koinApplication)
}