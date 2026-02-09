package com.lj.crud_supabase.data.repository.user

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Boolean
    suspend fun signUp(email: String, password: String): Boolean
    suspend fun signInWithGoogle(): Boolean

    //    fun exchangeCodeForSession(code: String)
    suspend fun exchangeCodeForSession(code: String): Result<Unit>

    suspend fun verifyEmail(tokenHash: String): Result<Unit>
    suspend fun signOut()

    fun getCurrentUserId(): String?

}