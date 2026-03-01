package com.lj.crud_supabase.domain.models

sealed interface AuthState {
    data object Initializing : AuthState
    data object Authenticated : AuthState
    data object Unauthenticated: AuthState
}