import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

/**
 * =====================================================
 * READ keystore.properties (KHÔNG COMMIT GIT)
 * =====================================================
 */
val localProperties = Properties().apply {
    val file = rootProject.file("keystore.properties")
    if (file.exists()) {
        file.inputStream().use { load(it) }
        logger.lifecycle("✅ keystore.properties loaded from ${file.absolutePath}")
    } else {
        logger.lifecycle("⚠️  keystore.properties NOT FOUND — release signing skipped")
    }
}

fun prop(key: String): String? = localProperties.getProperty(key)

android {
    namespace   = "com.xxx.base_mvvm"
    compileSdk  = 35

    defaultConfig {
        applicationId         = "com.xxx.base_mvvm"
        minSdk                = 24
        targetSdk             = 35
        versionCode           = 1
        versionName           = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        prop("RELEASE_STORE_FILE")?.let { storePath ->
            create("release") {
                storeFile     = rootProject.file(storePath)
                storePassword = prop("RELEASE_STORE_PASSWORD")
                keyAlias      = prop("RELEASE_KEY_ALIAS")
                keyPassword   = prop("RELEASE_KEY_PASSWORD")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled       = true
            isShrinkResources     = true
            signingConfigs.findByName("release")?.let { signingConfig = it }
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures { compose = true }
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    }
}

dependencies {
    // Core
    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:designsystem"))

    // Features
    implementation(project(":features:auth"))
    implementation(project(":features:home"))
    implementation(project(":features:detail"))
    implementation(project(":features:profile"))

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.activity)
    implementation(libs.compose.material3)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.tooling)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Navigation
    implementation(libs.navigation.compose)
    implementation(libs.hilt.navigation.compose)
}
