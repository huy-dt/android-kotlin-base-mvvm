package com.xxx.base_mvvm.core.network.model

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id")         val id: Int,
    @SerializedName("name")       val name: String,
    @SerializedName("email")      val email: String,
    @SerializedName("avatar_url") val avatarUrl: String = "",
    @SerializedName("created_at") val createdAt: Long = 0L
)

data class AuthResponseDto(
    @SerializedName("access_token")  val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("expires_in")    val expiresIn: Long
)

data class LoginRequestDto(
    @SerializedName("email")    val email: String,
    @SerializedName("password") val password: String
)
