// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath(libs.hilt.gradle)
        classpath(libs.google.services)
    }
}

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kover)
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin) apply false
}

tasks.create<Delete>("clean") {
    delete(rootProject.buildDir)
}

apply(plugin = "kover")