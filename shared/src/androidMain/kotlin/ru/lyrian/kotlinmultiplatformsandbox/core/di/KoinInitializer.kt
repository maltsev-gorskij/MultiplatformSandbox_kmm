package ru.lyrian.kotlinmultiplatformsandbox.core.di

import org.koin.core.KoinApplication
import org.koin.dsl.koinApplication

actual class KoinInitializer(koinApplication: KoinApplication.() -> Unit) {
    actual fun initializeKoin() = initializeSharedGraph { koinApplication() }
}