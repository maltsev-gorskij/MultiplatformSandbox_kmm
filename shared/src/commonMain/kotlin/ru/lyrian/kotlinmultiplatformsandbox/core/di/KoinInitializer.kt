package ru.lyrian.kotlinmultiplatformsandbox.core.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

// Android call
fun initializeKoin(koinApplication: KoinApplication.() -> Unit) {
    startKoin {
        koinApplication()
        modules(sharedModules())
    }
}

// IOS call
fun initializeKoin() = initializeKoin {  }
