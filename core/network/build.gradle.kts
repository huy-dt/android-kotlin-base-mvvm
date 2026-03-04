plugins {
    id("base.android.library")
    id("base.android.hilt")
}

android {
    namespace = "com.xxx.base_mvvm.core.network"
}

dependencies {
    implementation(project(":core:common"))
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp.logging)
    implementation(libs.coroutines.android)
    testImplementation(libs.junit)
}
