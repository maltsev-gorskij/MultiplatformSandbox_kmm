import gradle.kotlin.dsl.accessors._8441150fa878c79379b5c478c52580e2.kotlin
import org.gradle.kotlin.dsl.kotlin

plugins {
    kotlin("multiplatform")
    id("maven-publish")
}

group = "ru.lyrian.kotlinmultiplatformsandbox"
version = rootProject.extra["appVersion"] as String

kotlin {
    android {
        publishLibraryVariants("release", "debug")
    }
}