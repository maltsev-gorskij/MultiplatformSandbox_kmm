package ru.lyrian.kotlinmultiplatformsandbox.core.data.repository.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.lyrian.kotlinmultiplatformsandbox.core.data.repository.KmmDispatchers

internal val repositoryModule = module {
    factoryOf(::KmmDispatchers)
}