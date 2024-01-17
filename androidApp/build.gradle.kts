plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    kotlin("kapt")
    alias(libs.plugins.hiltGradlePlugin)
    alias(libs.plugins.serialization)
}

android {
    namespace = "com.example.translator_kmm.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.example.translator_kmm.android"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildToolsVersion = "34.0.0"
}

dependencies {
    implementation(projects.shared)
    implementation(libs.bundles.compose)
    debugImplementation(libs.compose.ui.tooling)

    implementation(libs.hilt.android)
    kapt(libs.hilt.androidCompiler)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation)

    implementation(libs.ktor.android)

    implementation(libs.testRunner)
    implementation(libs.jUnit)
    implementation(libs.composeTesting)
    debugImplementation(libs.composeTestManifest)

    kaptAndroidTest(libs.hilt.androidCompiler)
    androidTestImplementation(libs.hiltTesting)
}