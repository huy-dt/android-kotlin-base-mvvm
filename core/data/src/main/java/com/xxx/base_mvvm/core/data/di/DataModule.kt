package com.xxx.base_mvvm.core.data.di

import com.xxx.base_mvvm.core.data.repository.AuthRepositoryImpl
import com.xxx.base_mvvm.core.data.repository.UserRepositoryImpl
import com.xxx.base_mvvm.core.domain.repository.AuthRepository
import com.xxx.base_mvvm.core.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}