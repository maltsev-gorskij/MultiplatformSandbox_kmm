package ru.lyrian.kotlinmultiplatformsandbox.core.data.dataSource.database

import com.squareup.sqldelight.db.SqlDriver

internal expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}
