@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
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
    ksp(libs.hilt.compiler)

    testImplementation(libs.truth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.ext)
}
