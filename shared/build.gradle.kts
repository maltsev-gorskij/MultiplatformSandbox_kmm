plugins {
    // Platform plugins
    kotlin("plugin.serialization")

    // Convention plugins
    id("android-config")
    id("multiplatform-config")
    id("native-cocoapods-config")
    id("maven-publish-config")
    id("sqldelight-config")
    id("multiplatform-resources-config")
    id("moko-kswift-config")

    // Dependencies pluging
    id("com.rickclephas.kmp.nativecoroutines")
    id("co.touchlab.crashkios.crashlyticslink")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.datetime)
                implementation(libs.ktor.core)
                implementation(libs.bundles.ktor)
                implementation(libs.sqldelight.common)
                implementation(libs.koin.core)
                implementation(libs.multiplatform.settings)
                api(libs.moko.resources.common)
                implementation(libs.napier)
                implementation(libs.moko.paging)
                implementation(libs.crashkios)
                implementation(libs.gitlive.firestore)
                implementation(libs.gitlive.realtimedatabase)
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
