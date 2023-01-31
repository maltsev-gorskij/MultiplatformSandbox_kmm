import gradle.kotlin.dsl.accessors._658844fd496f8a227ec3e304207fa318.android
import gradle.kotlin.dsl.accessors._658844fd496f8a227ec3e304207fa318.java

plugins {
    id("com.android.library")
}

android {
    namespace = "ru.lyrian.kotlinmultiplatformsandbox"
    compileSdk = rootProject.extra["compileSdk"] as Int

    defaultConfig {
        minSdk = rootProject.extra["minSdk"] as Int
        targetSdk = rootProject.extra["targetSdk"] as Int
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            res.srcDirs(
                File(buildDir, "generated/moko/androidMain/res"),
                "src/androidMain/res",
                "src/commonMain/resources"
            )
        }
    }

    buildTypes {
        release {
            consumerProguardFiles("proguard-rules.pro")
        }
    }
}
