package com.ecohabits.data.repository

import android.util.Log
import com.ecohabits.data.remote.supabase.UserDto
import com.ecohabits.domain.repository.AuthRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.SignOutScope
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.IDToken
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.jsonPrimitive
import javax.inject.Inject
import com.ecohabits.domain.repository.AuthSessionState


/**
 * Implementación del repositorio de autenticación utilizando Supabase.
 *
 * Además de autenticar al usuario mediante Supabase Auth,
 * se asegura de que exista su correspondiente registro
 * en la tabla TblUser.
 */
class AuthRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : AuthRepository {


    override val authState: Flow<AuthSessionState> =
        supabaseClient.auth.sessionStatus.map { status ->

            when (status) {

                is SessionStatus.Authenticated -> {

                    try {

                        ensureUserExistsInDatabase()

                        Log.d(
                            "AuthRepository",
                            "Sesión restaurada y usuario sincronizado con TblUser."
                        )

                    } catch (e: Exception) {

                        Log.e(
                            "AuthRepository",
                            "No se pudo sincronizar el usuario con TblUser: ${e.message}",
                            e
                        )
                    }

                    AuthSessionState.Authenticated
                }

                is SessionStatus.NotAuthenticated -> {

                    AuthSessionState.Unauthenticated
                }

                else -> {

                    AuthSessionState.Loading
                }
            }
        }


    override suspend fun signUp(
        email: String,
        password: String,
        name: String,
        age: Int
    ): Result<Unit> {

        // TODO:
        // Implementar registro tradicional con email/password.

        return Result.success(Unit)
    }


    override suspend fun loginWithEmail(
        email: String,
        password: String
    ): Result<Unit> {

        // TODO:
        // Implementar inicio de sesión tradicional con email/password.

        return Result.success(Unit)
    }


    override suspend fun signInWithGoogle(
        idToken: String,
        nonce: String?
    ): Result<Unit> {

        return try {

            /*
             * 1. Autenticamos al usuario con Google
             * mediante Supabase Auth.
             */
            supabaseClient.auth.signInWith(IDToken) {

                this.idToken = idToken

                this.nonce = nonce

                this.provider = Google
            }


            /*
             * 2. Una vez autenticado correctamente,
             * comprobamos que exista también en TblUser.
             */
            ensureUserExistsInDatabase()


            Result.success(Unit)

        } catch (e: Exception) {

            Log.e(
                "AuthRepository",
                "Error iniciando sesión con Google: ${e.message}",
                e
            )

            Result.failure(e)
        }
    }


    private suspend fun ensureUserExistsInDatabase() {

        val authUser =
            supabaseClient.auth.currentUserOrNull()
                ?: throw IllegalStateException(
                    "No se encontró un usuario autenticado."
                )


        val authUserId = authUser.id


        /*
         * Primero comprobamos si TblUser ya posee
         * una fila con este IdUser.
         */
        val existingUsers =
            supabaseClient
                .postgrest["TblUser"]
                .select {

                    filter {

                        eq(
                            "IdUser",
                            authUserId
                        )
                    }
                }
                .decodeList<UserDto>()


        /*
         * Si ya existe, no debemos volver a insertarlo.
         *
         * Esto es importante porque en el futuro
         * TblUser contendrá datos como rachas,
         * progreso, etc.
         */
        if (existingUsers.isNotEmpty()) {

            Log.d(
                "AuthRepository",
                "El usuario $authUserId ya existe en TblUser."
            )

            return
        }


        /*
         * Obtenemos el nombre desde los metadatos
         * proporcionados por Google.
         */
        val fullName =
            authUser.userMetadata
                ?.get("full_name")
                ?.jsonPrimitive
                ?.content
                ?.trim()
                ?.takeIf {
                    it.isNotBlank()
                }
                ?: authUser.userMetadata
                    ?.get("name")
                    ?.jsonPrimitive
                    ?.content
                    ?.trim()
                    ?.takeIf {
                        it.isNotBlank()
                    }
                ?: "Usuario"

        val nameParts =
            fullName
                .split(" ")
                .filter {
                    it.isNotBlank()
                }


        val firstName =
            nameParts.firstOrNull()
                ?: "Usuario"


        val lastName =
            if (nameParts.size > 1) {

                nameParts
                    .drop(1)
                    .joinToString(" ")

            } else {

                ""
            }

        val userName =
            authUser.email
                ?.substringBefore("@")
                ?.takeIf {
                    it.isNotBlank()
                }
                ?: firstName.lowercase()

        val newUser = UserDto(

            idUser = authUserId,

            firstName = firstName,

            lastName = lastName,

            userName = userName,

            currentStreak = "0",

            maximumStreak = "0"
        )


        /*
         * Guardamos el usuario en TblUser.
         */
        supabaseClient
            .postgrest["TblUser"]
            .insert(newUser)


        Log.d(
            "AuthRepository",
            "Usuario $authUserId creado correctamente en TblUser."
        )
    }


    override suspend fun logout(): Result<Unit> {

        return try {

            supabaseClient.auth.signOut(
                SignOutScope.LOCAL
            )

            Result.success(Unit)

        } catch (e: Exception) {

            Log.e(
                "AuthRepository",
                "Error cerrando sesión: ${e.message}",
                e
            )

            Result.failure(e)
        }
    }


    override fun isUserLoggedIn(): Boolean {

        return supabaseClient
            .auth
            .currentSessionOrNull() != null
    }


    override fun getCurrentUserId(): String? {

        return supabaseClient
            .auth
            .currentUserOrNull()
            ?.id
    }


    override fun getCurrentUserName(): String? {

        val user =
            supabaseClient
                .auth
                .currentUserOrNull()


        return user
            ?.userMetadata
            ?.get("full_name")
            ?.jsonPrimitive
            ?.content

            ?: user
                ?.userMetadata
                ?.get("name")
                ?.jsonPrimitive
                ?.content
    }
}