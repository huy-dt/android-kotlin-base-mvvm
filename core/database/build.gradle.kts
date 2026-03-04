plugins {
    id("base.android.library")
    id("base.android.hilt")
}

android {
    namespace = "com.xxx.base_mvvm.core.database"
}

dependencies {
    implementation(project(":core:common"))
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    testImplementation(libs.junit)
}
