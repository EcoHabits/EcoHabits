package com.ecohabits.domain.repository

import com.ecohabits.data.local.entity.UserContextEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUser(): Flow<UserContextEntity?>
    suspend fun saveUser(userContextEntity: UserContextEntity)
    suspend fun clearUser()
}
