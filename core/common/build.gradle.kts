plugins {
    id("base.android.library")
}

android {
    namespace = "com.xxx.base_mvvm.core.common"
}

dependencies {
    // javax.inject chỉ để dùng @Qualifier — không cần Hilt
    implementation("javax.inject:javax.inject:1")
    implementation(libs.coroutines.android)
    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)
}
