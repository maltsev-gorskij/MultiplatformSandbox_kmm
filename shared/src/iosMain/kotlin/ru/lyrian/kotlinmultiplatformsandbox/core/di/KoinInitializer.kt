package ru.lyrian.kotlinmultiplatformsandbox.core.di

actual class KoinInitializer {
    actual fun initializeKoin() = initializeSharedGraph {}
}