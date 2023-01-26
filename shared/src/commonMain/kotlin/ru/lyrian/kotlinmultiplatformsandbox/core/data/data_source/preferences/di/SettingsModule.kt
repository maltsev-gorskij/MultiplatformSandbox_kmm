package ru.lyrian.kotlinmultiplatformsandbox.core.data.data_source.preferences.di

import org.koin.core.module.Module

internal expect class SettingsModule() {
    internal fun create(): Module
}
