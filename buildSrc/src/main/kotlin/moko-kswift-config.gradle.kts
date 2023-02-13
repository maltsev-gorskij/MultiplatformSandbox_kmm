import org.gradle.kotlin.dsl.kotlin

plugins {
    kotlin("multiplatform")
    id("dev.icerock.moko.kswift")
}

// Generating from ResourseState (Sealed Class) -> ResourceStateKs (Swift Enum class)
kswift {
    install(dev.icerock.moko.kswift.plugin.feature.SealedToSwiftEnumFeature) {
        filter = includeFilter("ClassContext/dev.icerock.moko:mvvm-state/dev/icerock/moko/mvvm/ResourceState", "ClassContext/ru.lyrian.kotlinmultiplatformsandbox:shared/ru/lyrian/kotlinmultiplatformsandbox/feature/responce_validation_test/KtorErrors")
    }
}