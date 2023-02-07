plugins {
    id("com.github.ben-manes.versions")
    id("io.gitlab.arturbosch.detekt")
}

// Where to delete ./build dir upon clean task call
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
    delete("${rootProject.projectDir}/buildSrc/build")
}

// Detect project dependencies updates
// Terminal command: ./gradlew dependencyUpdates
// Report path: build/dependencyUpdates/report.txt
fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version) && !isNonStable(currentVersion)
    }
}

// Register gradle task to check all files with detekt: ./gradlew detektAll
tasks.register<io.gitlab.arturbosch.detekt.Detekt>("detektAll") {
    parallel = true
    setSource(projectDir)
    include("**/*.kt", "**/*.kts")
    exclude("**/resources/**", "**/build/**")
    config.setFrom(project.file("config/detekt/detekt.yml"))
}

// Register task to run pre-commit detekt checks
task<Copy>("enableGitHooks") {
    group = "git hooks"
    from("${rootProject.rootDir}/hooks/")
    include("*")
    into("${rootProject.rootDir}/.git/hooks")
    fileMode = 0b111101101 // make files executable
}

// Unregister task to run pre-commit detekt checks
task<Delete>("disableGitHooks") {
    group = "git hooks"
    delete = setOf(
        fileTree("${rootProject.rootDir}/.git/hooks/") {
            include("*")
        }
    )
}

dependencies {
    detekt(libs.detekt.cli)
}

// Global project properties
val appVersion by extra("1.2")
val compileSdk by extra(33)
val minSdk by extra(21)
val targetSdk by extra(33)
