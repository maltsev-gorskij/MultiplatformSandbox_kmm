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

// Workaround for gradle task execition order errors
// https://regal-bone-e41.notion.site/Gradle-8-0-2-build-errors-c56741f89c6a402497a411707a8db2d1
afterEvaluate {
    tasks.findByName("androidDebugSourcesJar")!!.dependsOn("generateMRandroidMain")
    tasks.findByName("androidDebugSourcesJar")!!.dependsOn("generateMRcommonMain")

    tasks.findByName("androidReleaseSourcesJar")!!.dependsOn("generateMRandroidMain")
    tasks.findByName("androidReleaseSourcesJar")!!.dependsOn("generateMRcommonMain")

    tasks.findByName("iosArm64SourcesJar")!!.dependsOn("generateMRiosArm64Main")
    tasks.findByName("iosArm64SourcesJar")!!.dependsOn("generateMRcommonMain")

    tasks.findByName("iosSimulatorArm64SourcesJar")!!.dependsOn("generateMRiosSimulatorArm64Main")
    tasks.findByName("iosSimulatorArm64SourcesJar")!!.dependsOn("generateMRcommonMain")

    tasks.findByName("iosX64SourcesJar")!!.dependsOn("generateMRiosX64Main")
    tasks.findByName("iosX64SourcesJar")!!.dependsOn("generateMRcommonMain")

    tasks.findByName("sourcesJar")!!.dependsOn("generateMRandroidMain")
    tasks.findByName("sourcesJar")!!.dependsOn("generateMRiosArm64Main")
    tasks.findByName("sourcesJar")!!.dependsOn("generateMRiosSimulatorArm64Main")
    tasks.findByName("sourcesJar")!!.dependsOn("generateMRiosX64Main")
    tasks.findByName("sourcesJar")!!.dependsOn("generateMRcommonMain")
}
