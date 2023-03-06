import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
}

kotlin {
    cocoapods {
        version = rootProject.extra["appVersion"] as String
        summary = "Some description for a Kotlin/Native module"
        homepage = "https://github.com/maltsev-gorskij/MultiplatformSandbox_kmm"
        name = "shared"
        ios.deploymentTarget = "13.0"

        framework {
            baseName = "shared"
            isStatic = true
            export("dev.icerock.moko:resources:0.20.1")
        }

        xcodeConfigurationToNativeBuildType["CUSTOM_DEBUG"] = NativeBuildType.DEBUG
        xcodeConfigurationToNativeBuildType["CUSTOM_RELEASE"] = NativeBuildType.RELEASE
    }
}