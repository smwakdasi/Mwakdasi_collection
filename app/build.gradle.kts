plugins {
    alias(libs.plugins.android.application) // Android Application plugin
    alias(libs.plugins.kotlin.android) // Kotlin plugin
    alias(libs.plugins.compose.compiler) // Jetpack Compose Compiler plugin
    id("com.google.gms.google-services") // Google Services plugin required for Firebase
}

android {
    namespace = "com.example.mwakdasicollection"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mwakdasicollection"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner" // Default test runner
    }

    buildTypes {
        release {
            isMinifyEnabled = false // Disable code shrinking in release builds
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17 // Java 17 compatibility
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17" // JVM target for Kotlin
        languageVersion = "2.1" // Kotlin language version
    }

    buildFeatures {
        compose = true // Enable Jetpack Compose
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3" // Updated Compose Compiler version
    }
}

dependencies {
    // Firebase BoM (Bill of Materials ensures consistent versioning for Firebase components)
    implementation(platform(libs.firebase.bom))

    // Firebase Core Services
    implementation(libs.firebase.auth) // Firebase Authentication
    implementation(libs.firebase.firestore) // Firebase Firestore (Cloud Database)
    implementation(libs.firebase.storage) // Firebase Storage
    implementation(libs.firebase.messaging) // Firebase Cloud Messaging

    // Firebase KTX extensions for better Kotlin integration
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")

    // Core AndroidX & Jetpack Libraries
    implementation(libs.androidx.core.ktx) // Core KTX for Android extensions
    implementation(libs.androidx.appcompat) // AppCompat support
    implementation("androidx.recyclerview:recyclerview:1.3.2") // RecyclerView
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0") // ViewModel + KTX

    // Material Design components
    implementation("com.google.android.material:material:1.11.0") // Material components

    // Jetpack Compose Libraries
    implementation(platform("androidx.compose:compose-bom:2024.05.00")) // Compose BoM for dependencies

    // Required Compose modules
    implementation("androidx.compose.ui:ui") // Compose UI
    implementation("androidx.compose.material3:material3") // Material3 Components for Compose
    implementation("androidx.compose.ui:ui-tooling-preview") // Previewing UI
    implementation("androidx.activity:activity-compose") // Activity integration for Compose

    // Compose Navigation for handling navigation in a Compose app
    implementation("androidx.navigation:navigation-compose:2.7.3")

    // Kotlin Coroutines for asynchronous task handling
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Android Navigation (Fragment + XML approach)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.androidx.runtime.android)
    implementation(libs.play.services.analytics.impl)

    // Testing Libraries
    testImplementation(libs.junit) // JUnit for unit testing
    androidTestImplementation(libs.androidx.junit) // AndroidX JUnit extensions
    androidTestImplementation(libs.androidx.espresso.core) // Espresso for UI testing

    // Debugging tools for Jetpack Compose
    debugImplementation("androidx.compose.ui:ui-tooling") // Debug UI Tooling
    debugImplementation("androidx.compose.ui:ui-test-manifest") // Testing Manifest for Compose

    // Coil for image loading in Compose
    implementation ("io.coil-kt:coil-compose:2.5.0")
}