package com.xxx.base_mvvm.core.domain.model

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val avatarUrl: String = "",
    val createdAt: Long = 0L
)
