plugins {
    kotlin("multiplatform")
    id("dev.icerock.mobile.multiplatform-resources")
}

multiplatformResources {
    multiplatformResourcesPackage = "ru.lyrian.kotlinmultiplatformsandbox"
    multiplatformResourcesClassName = "Resources"
    iosBaseLocalizationRegion = "en" // optional, default "en"
}
