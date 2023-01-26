plugins {
    id("com.github.ben-manes.versions")
}

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
