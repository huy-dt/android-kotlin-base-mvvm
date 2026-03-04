plugins {
    id("base.android.library")
    id("base.android.hilt")
}

android {
    namespace = "com.xxx.base_mvvm.core.data"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:domain"))
    implementation(project(":core:database"))
    // network vẫn giữ cho UserRepository, bỏ khi chuyển hẳn sang offline
    implementation(project(":core:network"))
    implementation(libs.coroutines.android)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.turbine)
}
