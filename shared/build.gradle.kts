plugins {
    // platform plugins
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization")

    // Convention plugins
    id("android-config")
    id("multiplatform-config")
    id("native-cocoapods-config")
    id("maven-publish-config")
    id("sqldelight-config")
    id("multiplatform-resources-config")
    id("moko-kswift-config")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(libs.datetime)
                implementation(libs.bundles.ktor.common)
                implementation(libs.sqldelight.common)
                api(libs.koin.core)
                implementation(libs.multiplatform.settings)
                api(libs.moko.resources.common)
                implementation(libs.napier)
                api(libs.moko.paging)
                api(libs.moko.state)
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
