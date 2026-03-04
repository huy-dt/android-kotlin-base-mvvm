package com.xxx.base_mvvm.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey                         val id: Int,
    @ColumnInfo(name = "name")          val name: String,
    @ColumnInfo(name = "email")         val email: String,
    @ColumnInfo(name = "avatar_url")    val avatarUrl: String = "",
    @ColumnInfo(name = "created_at")    val createdAt: Long = 0L
)
