plugins {
    id("base.android.feature")
}

android {
    namespace = "com.xxx.base_mvvm.feature.profile"
}

dependencies {
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.turbine)
}
