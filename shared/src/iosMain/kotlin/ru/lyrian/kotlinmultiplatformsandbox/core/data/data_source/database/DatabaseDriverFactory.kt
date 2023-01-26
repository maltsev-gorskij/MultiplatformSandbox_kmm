package ru.lyrian.kotlinmultiplatformsandbox.core.data.data_source.database

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import ru.lyrian.kotlinmultiplatformsandbox.AppDatabase

internal actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(AppDatabase.Schema, "test.db")
    }
}
