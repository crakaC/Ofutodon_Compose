// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.kover)
    alias(libs.plugins.spotless)
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.ksp) apply false
    id("com.github.ben-manes.versions") version "0.52.0"
    id("nl.littlerobots.version-catalog-update") version "1.0.0"
}

allprojects {
    apply(plugin = rootProject.libs.plugins.spotless.get().pluginId)
    spotless {
        kotlin {
            target("**/*.kt")
            targetExclude("${layout.buildDirectory}/**/*.kt")
            ktlint()
        }
        kotlinGradle {
            target("**/*.kts")
            targetExclude("${layout.buildDirectory}/**/*.kts")
            ktlint()
        }
        format("xml") {
            target("**/*.xml")
            targetExclude("**/build/**/*.xml")
        }
    }
}
