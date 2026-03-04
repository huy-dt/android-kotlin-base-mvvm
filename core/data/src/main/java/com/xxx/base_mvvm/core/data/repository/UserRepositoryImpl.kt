package com.xxx.base_mvvm.core.data.repository

import com.xxx.base_mvvm.core.data.mapper.dtosToEntityList
import com.xxx.base_mvvm.core.data.mapper.entitiesToDomainList
import com.xxx.base_mvvm.core.data.mapper.toDomain
import com.xxx.base_mvvm.core.database.dao.UserDao
import com.xxx.base_mvvm.core.domain.model.User
import com.xxx.base_mvvm.core.domain.repository.UserRepository
import com.xxx.base_mvvm.core.network.service.UserApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService,
    private val userDao: UserDao
) : UserRepository {

    override fun getUsers(): Flow<List<User>> =
        userDao.getAll().map { it.entitiesToDomainList() }

    override fun getUserById(id: Int): Flow<User?> =
        userDao.getById(id).map { it?.toDomain() }

    override fun searchUsers(query: String): Flow<List<User>> =
        userDao.search(query).map { it.entitiesToDomainList() }

    override suspend fun refreshUsers() {
        val remote = userApiService.getUsers()
        userDao.deleteAll()
        userDao.insertAll(remote.dtosToEntityList())
    }
}
