@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    kotlin("android")
    id("kotlin-kapt")
}

apply(from = rootProject.file("gradle/android.gradle"))

android {
    namespace = "com.crakac.ofutodon.data.repository"
}

dependencies {
    implementation(project(":data:api"))
    implementation(libs.coroutines)
    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.okhttp)
    testImplementation(libs.retrofit.mock)
    testImplementation(libs.okhttp.mock)
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
}