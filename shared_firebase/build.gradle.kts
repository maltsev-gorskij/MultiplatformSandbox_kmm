import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType

plugins {
    // Platform plugins
    kotlin("plugin.serialization")
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("native.cocoapods")

    // Dependencies plugins
    id("maven-publish")
}

// Multiplatform configuration
kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()


    sourceSets {
        val commonMain by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting

        create("iosMain") {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
}

// Android sourceSet configuration
android {
    namespace = "ru.lyrian.kotlinmultiplatformsandbox_firebase"
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
}

// Multiplatform dependencies
kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.gitlive.firestore)
            }
        }
    }
}

// Cocoapods framework configuration
kotlin {
    cocoapods {
        version = rootProject.extra["appVersion"] as String
        summary = "Some description for a Kotlin/Native module"
        homepage = "https://github.com/maltsev-gorskij/MultiplatformSandbox_kmm"
        name = "shared_firebase"

        framework {
            baseName = "shared_firebase"
            isStatic = true
        }

        xcodeConfigurationToNativeBuildType["CUSTOM_DEBUG"] = NativeBuildType.DEBUG
        xcodeConfigurationToNativeBuildType["CUSTOM_RELEASE"] = NativeBuildType.RELEASE
    }
}

// Maven publishing configuration
group = "ru.lyrian.kotlinmultiplatformsandbox_firebase"
version = rootProject.extra["appVersion"] as String

kotlin {
    android {
        publishLibraryVariants("release", "debug")
    }
}