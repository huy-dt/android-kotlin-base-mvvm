package com.xxx.base_mvvm.core.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.xxx.base_mvvm.core.data.mapper.toDomain
import com.xxx.base_mvvm.core.domain.model.AuthToken
import com.xxx.base_mvvm.core.domain.model.User
import com.xxx.base_mvvm.core.domain.repository.AuthRepository
import com.xxx.base_mvvm.core.network.interceptor.TokenProvider
import com.xxx.base_mvvm.core.network.model.LoginRequestDto
import com.xxx.base_mvvm.core.network.service.AuthApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authApiService: AuthApiService,
    private val tokenProvider: TokenProvider,   // ← interface, không phụ thuộc OkHttp
    private val dataStore: DataStore<Preferences>
) : AuthRepository {

    companion object {
        val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")
    }

    override val currentUser: Flow<User?> = flowOf(null)

    override suspend fun login(email: String, password: String): AuthToken {
        val dto = authApiService.login(LoginRequestDto(email, password))
        val token = dto.toDomain()
        dataStore.edit { it[KEY_ACCESS_TOKEN] = token.accessToken }
        tokenProvider.setToken(token.accessToken)
        return token
    }

    override suspend fun logout() {
        runCatching { authApiService.logout() }
        dataStore.edit { it.remove(KEY_ACCESS_TOKEN) }
        tokenProvider.clearToken()
    }

    override suspend fun isLoggedIn(): Boolean =
        dataStore.data.map { it[KEY_ACCESS_TOKEN] }.first()?.isNotBlank() == true
}
