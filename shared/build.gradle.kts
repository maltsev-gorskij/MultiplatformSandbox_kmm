plugins {
    id("android-config")
    id("multiplatform-config")
    id("native-cocoapods-config")
    id("maven-publish-config")
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization")
    id("com.squareup.sqldelight")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.datetime)
                implementation(libs.bundles.ktor.common)
                implementation(libs.sqldelight.common)
                implementation(libs.sqldelight.coroutines)
                api(libs.koin.core)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.ktor.android)
                implementation(libs.sqldelight.android)
            }
        }

        iosMain {
            dependencies {
                implementation(libs.ktor.ios)
                implementation(libs.sqldelight.ios)
            }
        }
    }
}

sqldelight {
    database("AppDatabase") {
        packageName = "ru.lyrian.kotlinmultiplatformsandbox"
    }
}
