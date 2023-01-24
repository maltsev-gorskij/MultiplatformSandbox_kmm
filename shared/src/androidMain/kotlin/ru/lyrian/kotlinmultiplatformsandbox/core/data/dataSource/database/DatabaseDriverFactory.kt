package ru.lyrian.kotlinmultiplatformsandbox.core.data.dataSource.database

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import ru.lyrian.kotlinmultiplatformsandbox.AppDatabase

internal actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = AppDatabase.Schema,
            context = context,
            name = "test.db"
        )
    }
}
