package ru.lyrian.kotlinmultiplatformsandbox.core.data.dataSource.database.di

import com.squareup.sqldelight.db.SqlDriver
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.lyrian.kotlinmultiplatformsandbox.core.data.dataSource.database.DatabaseDriverFactory

internal actual class DatabaseDriverModule {
    internal actual fun create(): Module = module {
        single<SqlDriver> { DatabaseDriverFactory(get()).createDriver() }
    }
}
