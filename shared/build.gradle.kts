plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.serialization)
    alias(libs.plugins.sqlDelightGradlePlugin)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            binaryOption("bundleId", "com.example.translator_kmm.shared")
        }
    }
    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "16.0"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.ktorShared)
            implementation(libs.bundles.sqlDlightShared)
            implementation(libs.dateTime)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.bundles.testShared)
        }
        androidMain.dependencies {
            implementation(libs.ktor.android)
            implementation(libs.sqlDelight.androidDriver)
        }
        iosMain.dependencies {
            implementation(libs.ktor.ios)
            implementation(libs.sqlDelight.nativeDriver)
        }
    }
}

android {
    namespace = "com.example.translator_kmm"
    compileSdk = 34
    defaultConfig {
        minSdk = 28
    }
    buildToolsVersion = "34.0.0"
}

sqldelight {
    databases.create("TranslateDatabase") {
        packageName = "com.example.translator_kmm.database"
    }
}