@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    kotlin("android")
    id("kotlin-parcelize")
}

apply(from = rootProject.file("gradle/android.gradle"))

android {
    namespace = "com.crakac.ofutodon.data.api"
}

dependencies {
    implementation(libs.androidx.core)
    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.okhttp)
    testImplementation(libs.retrofit.mock)
    testImplementation(libs.okhttp.mock)
}