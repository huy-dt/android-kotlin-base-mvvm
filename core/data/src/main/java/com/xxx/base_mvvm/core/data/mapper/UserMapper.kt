package com.xxx.base_mvvm.core.data.mapper

import com.xxx.base_mvvm.core.database.entity.UserEntity
import com.xxx.base_mvvm.core.domain.model.AuthToken
import com.xxx.base_mvvm.core.domain.model.User
import com.xxx.base_mvvm.core.network.model.AuthResponseDto
import com.xxx.base_mvvm.core.network.model.UserDto

// DTO → Domain
fun UserDto.toDomain() = User(id = id, name = name, email = email, avatarUrl = avatarUrl, createdAt = createdAt)

// DTO → Entity
fun UserDto.toEntity() = UserEntity(id = id, name = name, email = email, avatarUrl = avatarUrl, createdAt = createdAt)

// Entity → Domain
fun UserEntity.toDomain() = User(id = id, name = name, email = email, avatarUrl = avatarUrl, createdAt = createdAt)

// Domain → Entity
fun User.toEntity() = UserEntity(id = id, name = name, email = email, avatarUrl = avatarUrl, createdAt = createdAt)

// Auth
fun AuthResponseDto.toDomain() = AuthToken(accessToken = accessToken, refreshToken = refreshToken, expiresIn = expiresIn)

// List mappers — tên khác nhau để tránh JVM signature clash (type erasure)
fun List<UserDto>.dtosToDomainList()     = map { it.toDomain() }
fun List<UserDto>.dtosToEntityList()     = map { it.toEntity() }
fun List<UserEntity>.entitiesToDomainList() = map { it.toDomain() }
