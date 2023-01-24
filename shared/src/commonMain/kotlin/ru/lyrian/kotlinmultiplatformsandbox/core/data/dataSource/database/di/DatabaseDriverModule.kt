package ru.lyrian.kotlinmultiplatformsandbox.core.data.dataSource.database.di

import org.koin.core.module.Module

internal expect class DatabaseDriverModule() {
    fun create(): Module
}
