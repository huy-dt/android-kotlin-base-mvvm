package com.xxx.base_mvvm.core.domain.repository

import com.xxx.base_mvvm.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<List<User>>
    fun getUserById(id: Int): Flow<User?>
    fun searchUsers(query: String): Flow<List<User>>
    suspend fun refreshUsers()
}
