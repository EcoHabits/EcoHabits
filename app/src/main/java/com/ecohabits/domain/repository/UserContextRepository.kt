package com.ecohabits.domain.repository

import com.ecohabits.domain.model.UserContext
import kotlinx.coroutines.flow.Flow

interface UserContextRepository {
    fun getUser(): Flow<UserContext?>
    suspend fun saveUser(userContext: UserContext)
    suspend fun clearUser()
}
