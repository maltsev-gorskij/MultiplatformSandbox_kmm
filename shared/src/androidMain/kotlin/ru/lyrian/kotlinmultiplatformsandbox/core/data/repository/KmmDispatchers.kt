package ru.lyrian.kotlinmultiplatformsandbox.core.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal actual class KmmDispatchers {
    @Suppress("FunctionMinLength")
    actual fun io(): CoroutineDispatcher = Dispatchers.IO

    actual fun default(): CoroutineDispatcher = Dispatchers.Default

    actual fun main(): CoroutineDispatcher = Dispatchers.Main
}