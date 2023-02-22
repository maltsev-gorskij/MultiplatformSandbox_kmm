package ru.lyrian.kotlinmultiplatformsandbox.core.initializers.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.lyrian.kotlinmultiplatformsandbox.core.initializers.FirebaseInitializer

actual val firebaseModule: Module = module {
    factoryOf(::FirebaseInitializer)
}