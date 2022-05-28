import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.crakac.ofutodon"
        minSdk = 26
        targetSdk = 32
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
                "retrofit2.pro"
            )
        }
        debug {
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.get()
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    namespace = "com.crakac.ofutodon"
}

dependencies {

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.bundles.androidx.lifecycle)
    implementation(libs.bundles.androidx.compose)

    implementation(libs.material)

    implementation(libs.bundles.accompanist)

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.interceptor)
    testImplementation(libs.retrofit.mock)
    testImplementation(libs.okhttp.mock)

    implementation(libs.coil)

    implementation(libs.hilt)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.hilt.compiler)

    implementation(libs.firebase.analytics)

    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.espresso)
    androidTestImplementation(libs.compose.junit)
    debugImplementation(libs.compose.tooling)
    debugImplementation(libs.compose.manifest)
}