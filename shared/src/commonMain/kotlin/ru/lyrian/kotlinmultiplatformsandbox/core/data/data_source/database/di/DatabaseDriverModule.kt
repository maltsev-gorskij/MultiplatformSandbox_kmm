package ru.lyrian.kotlinmultiplatformsandbox.core.data.data_source.database.di

import org.koin.core.module.Module

internal expect class DatabaseDriverModule() {
    internal fun create(): Module
}
