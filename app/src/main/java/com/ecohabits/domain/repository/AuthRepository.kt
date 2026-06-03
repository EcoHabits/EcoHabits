package com.ecohabits.domain.repository


interface AuthRepository {
    /**
     * Registra un nuevo usuario con los datos proporcionados
     */
    suspend fun signUp(email: String, password: String, name: String, age: Int): Result<Unit>

    /**
     * Inicia sesión con correo y contraseña (tradicional)
     */
    suspend fun loginWithEmail(email: String, password: String): Result<Unit>

    /**
     * Inicia sesión utilizando un ID Token de Google (OAuth)
     */
    suspend fun signInWithGoogle(idToken: String, nonce: String?): Result<Unit>

    /**
     * Cierra la sesión activa del usuario
     */
    suspend fun logout(): Result<Unit>

    /**
     * Verifica si hay una sesión activa
     */
    fun isUserLoggedIn(): Boolean

    /**
     * Obtiene el ID del usuario actual si existe
     */
    fun getCurrentUserId(): String?
}
