package com.xxx.base_mvvm.core.domain.repository

import com.xxx.base_mvvm.core.domain.model.AuthToken
import com.xxx.base_mvvm.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<User?>
    suspend fun login(email: String, password: String): AuthToken
    suspend fun logout()
    suspend fun isLoggedIn(): Boolean
}
