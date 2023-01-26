package ru.lyrian.kotlinmultiplatformsandbox.core.data.dataSource.preferences.di

import org.koin.core.module.Module

internal expect class SettingsModule() {
    internal fun create(): Module
}
