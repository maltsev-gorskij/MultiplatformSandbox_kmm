package ru.lyrian.kotlinmultiplatformsandbox.core.initializers

actual class KoinInitializer {
    actual fun initializeKoin() = initializeSharedGraph {}
}