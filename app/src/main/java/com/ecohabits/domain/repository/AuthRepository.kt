package com.ecohabits.domain.repository

import kotlinx.coroutines.flow.Flow

sealed interface AuthSessionState {

    data object Loading : AuthSessionState

    data object Authenticated : AuthSessionState

    data object Unauthenticated : AuthSessionState
}

interface AuthRepository {

    val authState: Flow<AuthSessionState>

    suspend fun signUp(
        email: String,
        password: String,
        name: String,
        age: Int
    ): Result<Unit>

    suspend fun loginWithEmail(
        email: String,
        password: String
    ): Result<Unit>

    suspend fun signInWithGoogle(
        idToken: String,
        nonce: String?
    ): Result<Unit>

    suspend fun logout(): Result<Unit>

    fun isUserLoggedIn(): Boolean

    fun getCurrentUserId(): String?

    fun getCurrentUserName(): String?
}