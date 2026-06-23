package com.ecohabits.data.repository

import com.ecohabits.data.local.dao.UserDAO
import com.ecohabits.data.local.entity.UserContextEntity
import com.ecohabits.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDAO: UserDAO
) : UserRepository {
    override fun getUser(): Flow<UserContextEntity?> = userDAO.getUser()

    override suspend fun saveUser(userContextEntity: UserContextEntity) {
        userDAO.upsertUser(userContextEntity)
    }

    override suspend fun clearUser() {
        userDAO.deleteUser()
    }
}