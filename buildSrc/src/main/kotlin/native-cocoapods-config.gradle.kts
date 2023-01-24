import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
}

kotlin {
    cocoapods {
        version = AppConfig.versionName
        summary = "Some description for a Kotlin/Native module"
        homepage = "https://github.com/maltsev-gorskij/MultiplatformSandbox_kmm"
        name = "SharedPod"

        framework {
            baseName = "shared"
            isStatic = false
        }

        xcodeConfigurationToNativeBuildType["CUSTOM_DEBUG"] = NativeBuildType.DEBUG
        xcodeConfigurationToNativeBuildType["CUSTOM_RELEASE"] = NativeBuildType.RELEASE
    }
}