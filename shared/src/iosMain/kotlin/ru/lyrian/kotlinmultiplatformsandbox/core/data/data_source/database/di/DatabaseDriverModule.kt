package ru.lyrian.kotlinmultiplatformsandbox.core.data.data_source.database.di

import com.squareup.sqldelight.db.SqlDriver
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.lyrian.kotlinmultiplatformsandbox.core.data.data_source.database.DatabaseDriverFactory

internal actual class DatabaseDriverModule {
    internal actual fun create(): Module = module {
        single<SqlDriver> { DatabaseDriverFactory().createDriver() }
    }
}
