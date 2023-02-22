plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
    maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(libs.gradleplugins.android)
    implementation(libs.gradleplugins.kotlin)
    implementation(libs.gradleplugins.sqldelight)
    implementation(libs.gradleplugins.kotlinxserialization)
    implementation(libs.gradleplugins.versions)
    implementation(libs.gradleplugins.cocoapods)
    implementation(libs.gradleplugins.detekt)
    implementation(libs.gradleplugins.mokoresources)
    implementation(libs.gradleplugins.kswift)
    implementation(libs.gradleplugins.nativecoroutines)
    implementation(libs.gradleplugins.crashkios)
}
