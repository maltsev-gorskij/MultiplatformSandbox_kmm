package ru.lyrian.kotlinmultiplatformsandbox.core.data.repository

import kotlinx.coroutines.CoroutineDispatcher

internal expect class KmmDispatchers constructor() {
    @Suppress("FunctionMinLength")
    fun io(): CoroutineDispatcher

    fun default(): CoroutineDispatcher

    fun main(): CoroutineDispatcher
}