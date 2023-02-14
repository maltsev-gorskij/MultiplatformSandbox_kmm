import org.gradle.kotlin.dsl.kotlin

plugins {
    kotlin("multiplatform")
    id("dev.icerock.moko.kswift")
}

// Generating from ResourseState (Sealed Class) -> ResourceStateKs (Swift Enum class)
@Suppress("MaxLineLength")
kswift {
    install(dev.icerock.moko.kswift.plugin.feature.SealedToSwiftEnumFeature) {
        filter = includeFilter(
            "ClassContext/ru.lyrian.kotlinmultiplatformsandbox:shared/ru/lyrian/kotlinmultiplatformsandbox/core/domain/SharedResult",
            "ClassContext/ru.lyrian.kotlinmultiplatformsandbox:shared/ru/lyrian/kotlinmultiplatformsandbox/core/data/pagination/PaginationState",
            "ClassContext/ru.lyrian.kotlinmultiplatformsandbox:shared/ru/lyrian/kotlinmultiplatformsandbox/core/exceptions/KtorExceptions",
            "ClassContext/ru.lyrian.kotlinmultiplatformsandbox:shared/ru/lyrian/kotlinmultiplatformsandbox/core/exceptions/DatabaseExceptions"
        )
    }
}