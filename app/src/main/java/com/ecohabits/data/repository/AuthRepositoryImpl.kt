package com.ecohabits.data.repository

import com.ecohabits.domain.repository.AuthRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.SignOutScope
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.IDToken
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementación del repositorio de autenticación utilizando Supabase
 */
class AuthRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : AuthRepository {

    override val authState: Flow<Boolean> = supabaseClient.auth.sessionStatus.map { status ->
        status is SessionStatus.Authenticated
    }


    override suspend fun signUp(email: String, password: String, name: String, age: Int): Result<Unit> {
        // TODO: Implementar registro tradicional con Supabase (email/password)
        // usar supabaseClient.auth.signUpWith(Email) y pasar data con name y age
        return Result.success(Unit)
    }

    override suspend fun loginWithEmail(email: String, password: String): Result<Unit> {
        // TODO: Implementar inicio de sesión tradicional con Supabase (email/password)
        // usar supabaseClient.auth.signInWith(Email)
        return Result.success(Unit)
    }

    override suspend fun signInWithGoogle(idToken: String, nonce: String?): Result<Unit> {
        return try {
            supabaseClient.auth.signInWith(IDToken){
                this.idToken = idToken
                this.nonce = nonce
                this.provider = Google
            }
            Result.success(Unit)
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun logout(): Result<Unit> {
        return try {
            supabaseClient.auth.signOut(SignOutScope.LOCAL)
            return Result.success(Unit)
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    override fun isUserLoggedIn(): Boolean {
        return supabaseClient.auth.currentSessionOrNull() != null
    }

    override fun getCurrentUserId(): String? {
        return supabaseClient.auth.currentUserOrNull()?.id
    }

    override fun getCurrentUserName(): String? {
        val user = supabaseClient.auth.currentUserOrNull()
        return user?.userMetadata?.get("full_name")?.jsonPrimitive?.content
            ?: user?.userMetadata?.get("name")?.jsonPrimitive?.content
    }
}
