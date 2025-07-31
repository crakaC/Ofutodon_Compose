import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

apply(from = rootProject.file("gradle/android.gradle"))

android {
    namespace = "com.crakac.ofutodon"

    defaultConfig {
        applicationId = "com.crakac.ofutodon"
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
                "retrofit2.pro",
            )
        }
        debug {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
                "retrofit2.pro",
            )
            applicationIdSuffix = ".debug"

            val propertyFile = rootProject.file("local.properties")
            if (!propertyFile.exists()) return@debug
            val properties = Properties()
            properties.load(FileInputStream(propertyFile))
            val token = properties["debug_token"]
            val host = properties["debug_host"]
            buildConfigField("String", "DEBUG_TOKEN", "\"$token\"")
            buildConfigField("String", "DEBUG_HOST", "\"$host\"")
        }
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":data:api"))
    implementation(project(":data:repository"))

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.bundles.androidx.lifecycle)
    implementation(libs.bundles.androidx.compose)

    implementation(libs.bundles.accompanist)

    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.okhttp)
    testImplementation(libs.retrofit.mock)
    testImplementation(libs.okhttp.mock)

    implementation(libs.coil)

    implementation(libs.hilt)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    implementation(libs.timber)

    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.espresso)
    androidTestImplementation(libs.androidx.compose.junit)
    debugImplementation(libs.androidx.compose.tooling)
    debugImplementation(libs.androidx.compose.manifest)
}
