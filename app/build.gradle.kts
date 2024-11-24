plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services) // Google Services plugin for Firebase
}

android {
    namespace = "com.example.motorcyclerental"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.motorcyclerental"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    packaging {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
}

dependencies {
    // Firebase BoM (Bill of Materials) for version management
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))

    // Firebase SDKs
    implementation("com.google.firebase:firebase-auth-ktx")       // Firebase Authentication
    implementation("com.google.firebase:firebase-firestore-ktx") // Firestore

    // Optional: Add if Realtime Database is required
    // implementation("com.google.firebase:firebase-database-ktx")

    // AndroidX Core and Lifecycle
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Jetpack Compose Libraries
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Navigation and Material Design
    implementation("androidx.navigation:navigation-compose:2.8.3")
    implementation("androidx.compose.material3:material3:1.1.1")
    implementation("androidx.compose.material:material-icons-extended:1.5.1")

    // Testing Libraries
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.1")
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.1")
}

// Apply the Google Services plugin for Firebase
apply(plugin = "com.google.gms.google-services")
