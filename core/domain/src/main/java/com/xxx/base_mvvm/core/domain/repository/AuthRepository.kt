package com.xxx.base_mvvm.core.domain.repository

import com.xxx.base_mvvm.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    /** User đang đăng nhập, null nếu chưa login */
    val currentUser: Flow<User?>

    /** Đăng nhập — trả về User nếu thành công, throw nếu sai */
    suspend fun login(email: String, password: String): User

    /** Đăng ký tài khoản mới — trả về User vừa tạo */
    suspend fun register(name: String, email: String, password: String): User

    suspend fun logout()
    suspend fun isLoggedIn(): Boolean
}
