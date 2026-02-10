package com.lj.crud_supabase.data.repository

import com.lj.crud_supabase.domain.model.AuthState
import kotlinx.coroutines.flow.StateFlow

interface AuthenticationRepository {
    val authState: StateFlow<AuthState>
    suspend fun signIn(email: String, password: String): Boolean
    suspend fun signUp(email: String, password: String): Boolean
    suspend fun signInWithGoogle(): Boolean
    suspend fun exchangeCodeForSession(code: String): Result<Unit>
    suspend fun verifyEmail(tokenHash: String): Result<Unit>
    suspend fun signOut()
}