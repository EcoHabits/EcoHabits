package com.ecohabits.domain.usecase.auth

import com.ecohabits.domain.repository.AuthRepository
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(idToken: String, nonce: String?): Result<Unit> {
        return repository.signInWithGoogle(idToken, nonce)
    }
}
