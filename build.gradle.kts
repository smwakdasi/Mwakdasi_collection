// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // Using version catalogs to manage plugin versions.
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false

    // Use the Google services Gradle plugin.
    id("com.google.gms.google-services") version "4.3.15" apply false
}

// Configure shared configuration for all sub-projects/modules
subprojects {
    // No repositories block here to comply with settings.gradle.kts configuration
}
