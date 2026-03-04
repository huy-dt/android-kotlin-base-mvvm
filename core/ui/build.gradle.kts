plugins {
    id("base.android.compose.library")
}

android {
    namespace = "com.xxx.base_mvvm.core.ui"
}

dependencies {
    api(project(":core:designsystem"))
    api(project(":core:common"))
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.runtime.compose)
}
