@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
}

apply(from = rootProject.file("gradle/android.gradle"))

android {
    namespace = "com.crakac.ofutodon.data.api"
}

dependencies {
    implementation(libs.androidx.core)
    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.okhttp)
    implementation(libs.kotlin.serialization.json)
    testImplementation(libs.retrofit.mock)
    testImplementation(libs.okhttp.mock)
}
