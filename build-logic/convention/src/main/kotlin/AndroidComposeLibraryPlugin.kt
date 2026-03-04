import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidComposeLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("base.android.library")
            apply("org.jetbrains.kotlin.plugin.compose")
        }
        extensions.configure<LibraryExtension> {
            buildFeatures { compose = true }
        }
        dependencies {
            val bom = platform("androidx.compose:compose-bom:2024.06.00")
            add("implementation", bom)
            add("implementation", "androidx.compose.ui:ui")
            add("implementation", "androidx.compose.ui:ui-tooling-preview")
            add("implementation", "androidx.compose.material3:material3")
            add("implementation", "androidx.compose.foundation:foundation")
            add("debugImplementation", "androidx.compose.ui:ui-tooling")
        }
    }
}
