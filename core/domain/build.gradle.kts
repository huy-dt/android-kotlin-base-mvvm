plugins {
    id("base.android.library")
    id("base.android.hilt")
}

android {
    namespace = "com.xxx.base_mvvm.core.domain"
}

dependencies {
    api(project(":core:common"))
    implementation(libs.coroutines.android)
    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
}
