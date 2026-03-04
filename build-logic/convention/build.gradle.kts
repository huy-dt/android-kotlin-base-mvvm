plugins {
    `kotlin-dsl`
}

group = "com.xxx.base_mvvm.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.plugins.android.application.get().let {
        "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}"
    })
    compileOnly(libs.plugins.android.library.get().let {
        "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}"
    })
    compileOnly(libs.plugins.kotlin.android.get().let {
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${it.version}"
    })
    compileOnly(libs.plugins.kotlin.compose.get().let {
        "org.jetbrains.kotlin:kotlin-compose-compiler-plugin-embeddable:${it.version}"
    })
    compileOnly(libs.plugins.hilt.get().let {
        "com.google.dagger:hilt-android-gradle-plugin:${it.version}"
    })
}

gradlePlugin {
    plugins {
        register("androidLibrary") {
            id = "base.android.library"
            implementationClass = "AndroidLibraryPlugin"
        }
        register("androidComposeLibrary") {
            id = "base.android.compose.library"
            implementationClass = "AndroidComposeLibraryPlugin"
        }
        register("androidFeature") {
            id = "base.android.feature"
            implementationClass = "AndroidFeaturePlugin"
        }
        register("androidHilt") {
            id = "base.android.hilt"
            implementationClass = "HiltConventionPlugin"
        }
    }
}
