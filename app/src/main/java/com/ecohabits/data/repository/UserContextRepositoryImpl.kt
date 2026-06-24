package com.ecohabits.data.repository

import com.ecohabits.data.local.dao.UserContextDao
import com.ecohabits.data.mapper.toDomain
import com.ecohabits.data.mapper.toEntity
import com.ecohabits.domain.model.UserContext
import com.ecohabits.domain.repository.UserContextRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserContextRepositoryImpl @Inject constructor(
    private val userDao: UserContextDao
) : UserContextRepository {
    override fun getUser(): Flow<UserContext?> {
        return userDao.getUser().map { it?.toDomain() }
    }

    override suspend fun saveUser(userContext: UserContext) {
        userDao.upsertUser(userContext.toEntity())
    }

    override suspend fun clearUser() {
        userDao.deleteUser()
    }
}
