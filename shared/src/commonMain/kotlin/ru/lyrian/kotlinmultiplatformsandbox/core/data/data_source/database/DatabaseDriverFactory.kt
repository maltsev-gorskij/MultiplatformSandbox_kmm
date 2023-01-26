package ru.lyrian.kotlinmultiplatformsandbox.core.data.data_source.database

import com.squareup.sqldelight.db.SqlDriver

internal expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}
