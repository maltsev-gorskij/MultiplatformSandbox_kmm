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
                api(libs.koin.core)
                implementation(libs.multiplatform.settings)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.ktor.android)
                implementation(libs.sqldelight.android)
                implementation(libs.security.crypto)
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
