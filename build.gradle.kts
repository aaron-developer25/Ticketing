// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
    id("com.google.devtools.ksp") version "2.0.20-1.0.24" apply false
    alias(libs.plugins.kotlinx.serialization) apply false
    id("androidx.room") version "2.6.1" apply false
}