import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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
    alias(libs.plugins.spotless)
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin) apply false
}

allprojects {
    apply(plugin = rootProject.libs.plugins.spotless.get().pluginId)
    spotless {
        kotlin {
            target("**/*.kt")
            targetExclude("$buildDir/**/*.kt")
            ktlint().userData(
                mapOf(
                    "disabled_rules" to "no-wildcard-imports,import-ordering,final-newline",
                )
            )
        }
        kotlinGradle {
            target("**/*.gradle.kts")
            ktlint().userData(
                mapOf(
                    "disabled_rules" to "no-wildcard-imports,import-ordering,final-newline",
                )
            )
        }
    }

    tasks {
        withType<KotlinCompile>().configureEach {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_11.toString()
                sourceCompatibility = JavaVersion.VERSION_11.toString()
                targetCompatibility = JavaVersion.VERSION_11.toString()
                freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
            }
        }
    }

    tasks.matching { it.name == "preBuild" }.all {
        dependsOn("spotlessKotlinApply")
    }
    tasks.matching { it.name == "prepareKotlinBuildScriptModel" }.all {
        dependsOn("spotlessKotlinGradleApply")
    }
}

apply(plugin = "kover")