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
    implementation(project(":core:network"))
    implementation(project(":core:database"))
    implementation(libs.coroutines.android)
    implementation(libs.datastore.preferences)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.turbine)
}
