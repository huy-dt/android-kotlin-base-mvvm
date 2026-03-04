# base_mvvm

Android project template — **Kotlin + Jetpack Compose + Hilt + KSP + MVVM**  
`minSdk 24` · `targetSdk 35`

## Cấu trúc module

```
app/
core/
  ├── common        – Result, Dispatcher, NetworkMonitor, Extensions
  ├── domain        – Model, Repository interface, UseCase base + impls
  ├── data          – RepositoryImpl, Mapper, DataStore
  ├── network       – Retrofit, DTO, Interceptor
  ├── database      – Room, Entity, DAO
  ├── designsystem  – Theme, Color, Typography
  └── ui            – Shared Compose components
features/
  ├── auth          – Login screen
  ├── home          – Danh sách user
  ├── detail        – Chi tiết user
  └── profile       – Hồ sơ + logout
build-logic/        – Convention plugins (AndroidLibrary, AndroidFeature, Hilt)
```

## Dependency graph

```
app → features/* → core:domain → core:common
                               → core:data → core:network
                                           → core:database
              → core:ui → core:designsystem
```

## Tech stack

| Thư viện | Phiên bản |
|---|---|
| Kotlin | 2.0.0 |
| Compose BOM | 2024.06.00 |
| Hilt | 2.51.1 |
| KSP | 2.0.0-1.0.21 |
| Room | 2.6.1 |
| Retrofit | 2.11.0 |
| Navigation Compose | 2.7.7 |
| DataStore | 1.1.1 |

## Quy tắc kiến trúc

- **UiState** — data class bất biến, toàn bộ trạng thái UI
- **UiEvent** — sealed class cho sự kiện 1 lần (navigate, snackbar)
- **ViewModel** — dùng `StateFlow` + `Channel`, không biết về Compose
- **UseCase** — mỗi class 1 việc, extends `FlowUseCase` hoặc `SuspendUseCase`
- **Repository** — interface ở `core:domain`, impl ở `core:data`
- **Navigation** — extension functions trên `NavGraphBuilder`, `app` ghép lại trong `AppNavHost`

## Cách thêm feature mới

1. Tạo module `:features:ten_feature`
2. Apply plugin `id("base.android.feature")` trong `build.gradle.kts`
3. Tạo `State`, `Event`, `ViewModel`, `Screen`, `Navigation`
4. Đăng ký trong `settings.gradle.kts`
5. Thêm `implementation(project(":features:ten_feature"))` vào `app/build.gradle.kts`
6. Gọi extension trong `AppNavHost.kt`
