import gradle.kotlin.dsl.accessors._ee9bc90780502274d01e59471d2213f0.kotlin
import gradle.kotlin.dsl.accessors._ee9bc90780502274d01e59471d2213f0.sourceSets
import org.gradle.kotlin.dsl.getting
import org.gradle.kotlin.dsl.withType

plugins {
    id("com.android.library")
    id("kotlin-multiplatform")
}

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
