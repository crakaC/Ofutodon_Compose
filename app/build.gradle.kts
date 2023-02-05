import java.io.FileInputStream
import java.util.*

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("android")
    id("com.android.application")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
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
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
                "retrofit2.pro",
            )
            applicationIdSuffix = ".debug"
            val properties = Properties().apply {
                load(FileInputStream(rootProject.file("local.properties")))
            }
            val token = properties.getProperty("debug_token")
            val host = properties.getProperty("debug_host")
            buildConfigField("String", "DEBUG_TOKEN", "\"$token\"")
            buildConfigField("String", "DEBUG_HOST", "\"$host\"")
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
    }

    packagingOptions {
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

    implementation(libs.material)

    implementation(libs.bundles.accompanist)

    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.okhttp)
    testImplementation(libs.retrofit.mock)
    testImplementation(libs.okhttp.mock)

    implementation(libs.coil)

    implementation(libs.hilt)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.hilt.compiler)

    implementation(libs.firebase.analytics)

    implementation(libs.timber)

    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.espresso)
    androidTestImplementation(libs.androidx.compose.junit)
    debugImplementation(libs.androidx.compose.tooling)
    debugImplementation(libs.androidx.compose.manifest)
}
