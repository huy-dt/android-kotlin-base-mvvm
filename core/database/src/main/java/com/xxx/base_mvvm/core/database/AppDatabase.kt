package com.xxx.base_mvvm.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xxx.base_mvvm.core.database.dao.AccountDao
import com.xxx.base_mvvm.core.database.dao.UserDao
import com.xxx.base_mvvm.core.database.entity.AccountEntity
import com.xxx.base_mvvm.core.database.entity.UserEntity

@Database(
    entities = [UserEntity::class, AccountEntity::class],
    version  = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun accountDao(): AccountDao

    companion object {
        const val DATABASE_NAME = "base_mvvm.db"
    }
}
