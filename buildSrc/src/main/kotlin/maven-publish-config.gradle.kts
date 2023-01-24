plugins {
    kotlin("multiplatform")
    id("maven-publish")
}

group = "ru.lyrian.kotlinmultiplatformsandbox"
version = AppConfig.versionName

kotlin {
    android {
        publishLibraryVariants("release", "debug")
    }
}