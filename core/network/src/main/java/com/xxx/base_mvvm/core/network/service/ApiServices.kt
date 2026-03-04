package com.xxx.base_mvvm.core.network.service

import com.xxx.base_mvvm.core.network.model.AuthResponseDto
import com.xxx.base_mvvm.core.network.model.LoginRequestDto
import com.xxx.base_mvvm.core.network.model.UserDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApiService {
    @GET("users")
    suspend fun getUsers(): List<UserDto>

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Int): UserDto

    @GET("users")
    suspend fun searchUsers(@Query("q") query: String): List<UserDto>
}

interface AuthApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequestDto): AuthResponseDto

    @POST("auth/logout")
    suspend fun logout()
}
