package com.lj.crud_supabase.data.repository.user

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.admin.AdminUserBuilder
//import io.github.jan.supabase.auth.providers.Email
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.Email
import jakarta.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: Auth
) : AuthRepository {
    override suspend fun signIn(email: String, password: String): Boolean {
        return try {
            auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun signUp(email: String, password: String): Boolean {
        return try {
            auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun signInWithGoogle(): Boolean {
        return try {
            auth.signInWith(Google)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun exchangeCodeForSession(code: String): Result<Unit> =
        runCatching {
            auth.exchangeCodeForSession(code = code, saveSession = true)
            return Result.success(Unit)
        }.onFailure {
            return Result.failure(it)
        }

    override suspend fun verifyEmail(tokenHash: String): Result<Unit> = runCatching {
        auth.verifyEmailOtp(
            type = OtpType.Email.EMAIL,
            tokenHash = tokenHash
        )
        return Result.success(Unit)
    }.onFailure { e ->
        return Result.failure(e)
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override fun getCurrentUserId(): String? {
        return auth.currentUserOrNull()?.id
    }
}