package com.xxx.base_mvvm.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.xxx.base_mvvm.core.database.entity.AccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Query("SELECT * FROM accounts WHERE email = :email LIMIT 1")
    suspend fun getByEmail(email: String): AccountEntity?

    @Query("SELECT * FROM accounts WHERE is_logged_in = 1 LIMIT 1")
    fun getLoggedInAccount(): Flow<AccountEntity?>

    @Query("SELECT * FROM accounts WHERE is_logged_in = 1 LIMIT 1")
    suspend fun getLoggedInAccountOnce(): AccountEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(account: AccountEntity): Long

    @Query("UPDATE accounts SET is_logged_in = 1 WHERE email = :email")
    suspend fun setLoggedIn(email: String)

    @Query("UPDATE accounts SET is_logged_in = 0")
    suspend fun logoutAll()

    @Query("SELECT EXISTS(SELECT 1 FROM accounts WHERE email = :email)")
    suspend fun isEmailTaken(email: String): Boolean
}
