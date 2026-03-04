package com.xxx.base_mvvm.core.data.repository

import com.xxx.base_mvvm.core.database.dao.AccountDao
import com.xxx.base_mvvm.core.domain.model.User
import com.xxx.base_mvvm.core.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.security.MessageDigest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val accountDao: AccountDao
) : AuthRepository {

    // ── currentUser ────────────────────────────────────────────────────────────
    override val currentUser: Flow<User?> =
        accountDao.getLoggedInAccount().map { it?.toUser() }

    // ── login ──────────────────────────────────────────────────────────────────
    override suspend fun login(email: String, password: String): User {
        val account = accountDao.getByEmail(email.trim().lowercase())
            ?: throw IllegalArgumentException("Email không tồn tại")

        if (account.passwordHash != hashPassword(password))
            throw IllegalArgumentException("Mật khẩu không đúng")

        accountDao.setLoggedIn(account.email)
        return account.toUser()
    }

    // ── register ───────────────────────────────────────────────────────────────
    override suspend fun register(name: String, email: String, password: String): User {
        val normalizedEmail = email.trim().lowercase()

        if (accountDao.isEmailTaken(normalizedEmail))
            throw IllegalArgumentException("Email đã được sử dụng")

        val newAccount = com.xxx.base_mvvm.core.database.entity.AccountEntity(
            name         = name.trim(),
            email        = normalizedEmail,
            passwordHash = hashPassword(password),
            isLoggedIn   = true
        )
        val insertedId = accountDao.insert(newAccount)
        return newAccount.copy(id = insertedId.toInt()).toUser()
    }

    // ── logout ─────────────────────────────────────────────────────────────────
    override suspend fun logout() {
        accountDao.logoutAll()
    }

    // ── isLoggedIn ─────────────────────────────────────────────────────────────
    override suspend fun isLoggedIn(): Boolean =
        accountDao.getLoggedInAccountOnce() != null

    // ── helpers ────────────────────────────────────────────────────────────────
    private fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val bytes  = digest.digest(password.toByteArray(Charsets.UTF_8))
        return bytes.joinToString("") { "%02x".format(it) }
    }

    private fun com.xxx.base_mvvm.core.database.entity.AccountEntity.toUser() = User(
        id        = id,
        name      = name,
        email     = email,
        createdAt = createdAt
    )
}
