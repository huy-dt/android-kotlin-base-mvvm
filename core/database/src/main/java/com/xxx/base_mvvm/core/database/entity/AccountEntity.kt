package com.xxx.base_mvvm.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Lưu thông tin đăng nhập local — password được hash trước khi lưu.
 * Index unique trên email để tránh đăng ký trùng.
 */
@Entity(
    tableName = "accounts",
    indices = [Index(value = ["email"], unique = true)]
)
data class AccountEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")             val id: Int = 0,
    @ColumnInfo(name = "email")          val email: String,
    @ColumnInfo(name = "password_hash")  val passwordHash: String,
    @ColumnInfo(name = "name")           val name: String,
    @ColumnInfo(name = "created_at")     val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "is_logged_in")   val isLoggedIn: Boolean = false
)
