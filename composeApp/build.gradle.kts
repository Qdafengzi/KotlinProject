import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)

//    kotlin("jvm") version "2.0.0"
//    id("org.jetbrains.kotlin.jvm") version "2.0.0"

}

compose.resources {
    publicResClass = true
    packageOfResClass = "org.example.kmpapp"
    generateResClass = always
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            // CameraX core library using the camera2 implementation
            val cameraxVersion = "1.4.0-beta02"
            // The following line is optional, as the core library is included indirectly by camera-camera2
            implementation("androidx.camera:camera-core:${cameraxVersion}")
            implementation("androidx.camera:camera-camera2:${cameraxVersion}")
            // If you want to additionally use the CameraX Lifecycle library
            implementation("androidx.camera:camera-lifecycle:${cameraxVersion}")
            // If you want to additionally use the CameraX VideoCapture library
            implementation("androidx.camera:camera-video:${cameraxVersion}")
            // If you want to additionally use the CameraX View class
            implementation("androidx.camera:camera-view:${cameraxVersion}")
            // If you want to additionally add CameraX ML Kit Vision Integration
            implementation("androidx.camera:camera-mlkit-vision:${cameraxVersion}")
            // If you want to additionally use the CameraX Extensions library
            implementation("androidx.camera:camera-extensions:${cameraxVersion}")
            implementation(libs.ktor.client.okhttp)
            implementation(libs.kotlinx.coroutines.android)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.ktor.client.core)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.koin)
            implementation(libs.serialization)
            implementation(libs.kamel)

            implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.7.0-alpha07")
            implementation("io.github.oshai:kotlin-logging:7.0.0")

            implementation("dev.icerock.moko:permissions:0.18.0")
            // compose multiplatform
            implementation("dev.icerock.moko:permissions-compose:0.18.0") // permissions api + compose extensions
            implementation("dev.icerock.moko:permissions-test:0.18.0")
            //bluetooth
            //implementation("com.juul.kable:kable-core:0.32.0")
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "org.example.kmpapp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "org.example.kmpapp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}

