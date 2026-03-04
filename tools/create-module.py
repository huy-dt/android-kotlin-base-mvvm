#!/usr/bin/env python3
"""
Android Module Generator - MVVM + Clean Architecture
Tạo nhanh feature/core module với đầy đủ folder và file cần thiết
Usage: python create_module.py
"""

import os
import sys

# ─────────────────────────────────────────────
# TEMPLATES
# ─────────────────────────────────────────────

def build_gradle_feature(package: str, module_name: str) -> str:
    return f"""\
plugins {{
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}}

android {{
    namespace = "{package}"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {{
        minSdk = libs.versions.minSdk.get().toInt()
    }}

    compileOptions {{
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }}
    kotlinOptions {{
        jvmTarget = "17"
    }}
}}

dependencies {{
    implementation(project(":core:common"))
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
}}
"""

def build_gradle_core(package: str, module_name: str) -> str:
    return f"""\
plugins {{
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}}

android {{
    namespace = "{package}"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {{
        minSdk = libs.versions.minSdk.get().toInt()
    }}

    compileOptions {{
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }}
    kotlinOptions {{
        jvmTarget = "17"
    }}
}}

dependencies {{
    implementation(project(":core:common"))

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}}
"""

def viewmodel_kt(package: str, name: str) -> str:
    cap = name.capitalize()
    return f"""\
package {package}.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class {cap}ViewModel @Inject constructor(
    // private val useCase: Get{cap}UseCase
) : ViewModel() {{

    private val _uiState = MutableStateFlow<{cap}UiState>({cap}UiState.Loading)
    val uiState: StateFlow<{cap}UiState> = _uiState.asStateFlow()

}}

sealed class {cap}UiState {{
    object Loading : {cap}UiState()
    data class Success(val data: Unit) : {cap}UiState() // TODO: replace Unit
    data class Error(val message: String) : {cap}UiState()
}}
"""

def repository_kt(package: str, name: str) -> str:
    cap = name.capitalize()
    return f"""\
package {package}.data.repository

import {package}.domain.repository.{cap}Repository
import javax.inject.Inject

class {cap}RepositoryImpl @Inject constructor(
    // private val remoteDataSource: {cap}RemoteDataSource
) : {cap}Repository {{

    // TODO: implement
}}
"""

def repository_interface_kt(package: str, name: str) -> str:
    cap = name.capitalize()
    return f"""\
package {package}.domain.repository

interface {cap}Repository {{
    // TODO: define methods
}}
"""

def usecase_kt(package: str, name: str) -> str:
    cap = name.capitalize()
    return f"""\
package {package}.domain.usecase

import {package}.domain.repository.{cap}Repository
import javax.inject.Inject

class Get{cap}UseCase @Inject constructor(
    private val repository: {cap}Repository
) {{
    suspend operator fun invoke() {{
        // TODO: implement
    }}
}}
"""

def hilt_module_kt(package: str, name: str, is_feature: bool) -> str:
    cap = name.capitalize()
    if is_feature:
        return f"""\
package {package}.di

import {package}.data.repository.{cap}RepositoryImpl
import {package}.domain.repository.{cap}Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class {cap}Module {{

    @Binds
    @Singleton
    abstract fun bind{cap}Repository(
        impl: {cap}RepositoryImpl
    ): {cap}Repository
}}
"""
    else:
        return f"""\
package {package}.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object {cap}Module {{
    // TODO: provide dependencies
}}
"""

def manifest_xml(package: str) -> str:
    return f"""\
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android" />
"""

def proguard_txt() -> str:
    return "# Add project specific ProGuard rules here.\n"


# ─────────────────────────────────────────────
# HELPERS
# ─────────────────────────────────────────────

def write(path: str, content: str):
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, "w", encoding="utf-8") as f:
        f.write(content)
    print(f"  [+] {path}")

def pkg_to_path(package: str) -> str:
    return package.replace(".", "/")


# ─────────────────────────────────────────────
# GENERATORS
# ─────────────────────────────────────────────

def create_feature_module(root: str, module_name: str, base_package: str):
    """Tạo feature module: feature/<module_name>"""
    package = f"{base_package}.feature.{module_name}"
    pkg_path = pkg_to_path(package)
    module_dir = os.path.join(root, "feature", module_name)
    src = os.path.join(module_dir, "src", "main")
    kotlin_src = os.path.join(src, "kotlin", pkg_path)

    print(f"\n📦 Tạo feature module: feature:{module_name}")

    # build.gradle.kts
    write(os.path.join(module_dir, "build.gradle.kts"),
          build_gradle_feature(package, module_name))

    # AndroidManifest
    write(os.path.join(src, "AndroidManifest.xml"), manifest_xml(package))

    # proguard
    write(os.path.join(module_dir, "proguard-rules.pro"), proguard_txt())

    cap = module_name.capitalize()

    # ViewModel
    write(os.path.join(kotlin_src, "presentation", f"{cap}ViewModel.kt"),
          viewmodel_kt(package, module_name))

    # Repository interface
    write(os.path.join(kotlin_src, "domain", "repository", f"{cap}Repository.kt"),
          repository_interface_kt(package, module_name))

    # Repository impl
    write(os.path.join(kotlin_src, "data", "repository", f"{cap}RepositoryImpl.kt"),
          repository_kt(package, module_name))

    # UseCase
    write(os.path.join(kotlin_src, "domain", "usecase", f"Get{cap}UseCase.kt"),
          usecase_kt(package, module_name))

    # Hilt Module
    write(os.path.join(kotlin_src, "di", f"{cap}Module.kt"),
          hilt_module_kt(package, module_name, is_feature=True))

    print(f"  ✅ feature:{module_name} xong!")
    return f':feature:{module_name}'


def create_core_module(root: str, module_name: str, base_package: str):
    """Tạo core module: core/<module_name>"""
    package = f"{base_package}.core.{module_name}"
    pkg_path = pkg_to_path(package)
    module_dir = os.path.join(root, "core", module_name)
    src = os.path.join(module_dir, "src", "main")
    kotlin_src = os.path.join(src, "kotlin", pkg_path)

    print(f"\n📦 Tạo core module: core:{module_name}")

    # build.gradle.kts
    write(os.path.join(module_dir, "build.gradle.kts"),
          build_gradle_core(package, module_name))

    # AndroidManifest
    write(os.path.join(src, "AndroidManifest.xml"), manifest_xml(package))

    # proguard
    write(os.path.join(module_dir, "proguard-rules.pro"), proguard_txt())

    cap = module_name.capitalize()

    # Hilt Module
    write(os.path.join(kotlin_src, "di", f"{cap}Module.kt"),
          hilt_module_kt(package, module_name, is_feature=False))

    print(f"  ✅ core:{module_name} xong!")
    return f':core:{module_name}'


def update_settings_gradle(root: str, includes: list[str]):
    """Thêm include(...) vào settings.gradle.kts nếu chưa có"""
    settings_path = os.path.join(root, "settings.gradle.kts")
    if not os.path.exists(settings_path):
        print(f"\n⚠️  Không tìm thấy settings.gradle.kts tại {settings_path}")
        print("   Tự thêm include sau vào settings.gradle.kts:")
        for inc in includes:
            print(f'   include("{inc}")')
        return

    with open(settings_path, "r", encoding="utf-8") as f:
        content = f.read()

    added = []
    for inc in includes:
        line = f'include("{inc}")'
        if line not in content:
            content += f"\n{line}"
            added.append(line)

    if added:
        with open(settings_path, "w", encoding="utf-8") as f:
            f.write(content)
        print(f"\n📝 Đã thêm vào settings.gradle.kts:")
        for a in added:
            print(f"   {a}")
    else:
        print(f"\n📝 settings.gradle.kts: tất cả module đã tồn tại, bỏ qua.")


# ─────────────────────────────────────────────
# MAIN
# ─────────────────────────────────────────────

def main():
    print("=" * 50)
    print("  Android Module Generator - MVVM Clean Arch")
    print("=" * 50)

    # Root project
    root = input("\n📁 Đường dẫn root project (Enter = thư mục hiện tại): ").strip()
    if not root:
        root = os.getcwd()
    if not os.path.isdir(root):
        print(f"❌ Không tìm thấy thư mục: {root}")
        sys.exit(1)

    # Base package
    base_package = input("📦 Base package (vd: com.example.app): ").strip()
    if not base_package:
        base_package = "com.example.app"

    includes = []

    while True:
        print("\n" + "-" * 40)
        module_type = input("Tạo module loại gì? [f=feature / c=core / q=thoát]: ").strip().lower()

        if module_type == "q":
            break
        elif module_type == "f":
            name = input("  Tên module (vd: profile, payment): ").strip().lower()
            if name:
                inc = create_feature_module(root, name, base_package)
                includes.append(inc)
        elif module_type == "c":
            name = input("  Tên module (vd: analytics, logging): ").strip().lower()
            if name:
                inc = create_core_module(root, name, base_package)
                includes.append(inc)
        else:
            print("  ⚠️  Nhập f, c hoặc q")

    if includes:
        update_settings_gradle(root, includes)
        print("\n🎉 Hoàn tất! Sync Gradle là xong.")
    else:
        print("\n👋 Không có module nào được tạo.")


if __name__ == "__main__":
    main()