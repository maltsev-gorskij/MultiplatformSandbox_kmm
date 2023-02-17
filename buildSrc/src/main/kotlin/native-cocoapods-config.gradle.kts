import gradle.kotlin.dsl.accessors._b7dceff322b6b2884f430c7bbb2ba83b.cocoapods
import gradle.kotlin.dsl.accessors._b7dceff322b6b2884f430c7bbb2ba83b.kotlin
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

        framework {
            baseName = "shared"
            isStatic = false

            export("dev.icerock.moko:resources:0.20.1")
        }

        xcodeConfigurationToNativeBuildType["CUSTOM_DEBUG"] = NativeBuildType.DEBUG
        xcodeConfigurationToNativeBuildType["CUSTOM_RELEASE"] = NativeBuildType.RELEASE
    }
}