package ru.lyrian.kotlinmultiplatformsandbox.core.di

import org.koin.core.KoinApplication
import org.koin.dsl.koinApplication

actual class KoinInitializer(private val koinApplication: KoinApplication.() -> Unit) {
    actual fun initializeKoin() = initializeSharedGraph(koinApplication = koinApplication)
}