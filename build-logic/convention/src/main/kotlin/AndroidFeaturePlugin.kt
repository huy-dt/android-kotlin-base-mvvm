import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("base.android.library")
            apply("base.android.hilt")
            apply("org.jetbrains.kotlin.plugin.compose")
        }
        extensions.configure<LibraryExtension> {
            buildFeatures { compose = true }
        }
        dependencies {
            // Core modules
            add("implementation", project(":core:domain"))
            add("implementation", project(":core:ui"))
            add("implementation", project(":core:designsystem"))
            add("implementation", project(":core:common"))

            // Compose BOM — phải có để resolve tất cả compose artifacts
            val bom = platform("androidx.compose:compose-bom:2024.06.00")
            add("implementation", bom)
            add("androidTestImplementation", bom)

            add("implementation", "androidx.compose.ui:ui")
            add("implementation", "androidx.compose.ui:ui-tooling-preview")
            add("implementation", "androidx.compose.material3:material3")
            add("implementation", "androidx.compose.foundation:foundation")
            add("debugImplementation", "androidx.compose.ui:ui-tooling")

            // Navigation + Hilt Navigation
            add("implementation", "androidx.navigation:navigation-compose:2.7.7")
            add("implementation", "androidx.hilt:hilt-navigation-compose:1.2.0")

            // Lifecycle
            add("implementation", "androidx.lifecycle:lifecycle-viewmodel-compose:2.8.2")
            add("implementation", "androidx.lifecycle:lifecycle-runtime-compose:2.8.2")
        }
    }
}
