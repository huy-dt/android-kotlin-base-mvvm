package com.xxx.base_mvvm.core.domain.model

data class AuthToken(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long
)
