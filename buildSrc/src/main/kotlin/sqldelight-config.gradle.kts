plugins {
    kotlin("multiplatform")
    id("com.squareup.sqldelight")
}

sqldelight {
    database("AppDatabase") {
        packageName = "ru.lyrian.kotlinmultiplatformsandbox"
        verifyMigrations = true
    }
}
