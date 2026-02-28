package com.lj.crud_supabase.presentation.feature.cashier

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lj.crud_supabase.data.repository.AuthenticationRepository
import com.lj.crud_supabase.domain.models.AuthState
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class CashierViewModel @Inject constructor(

    private val authRepository: AuthenticationRepository
) : ViewModel() {


    val authState: StateFlow<AuthState> = authRepository.authState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = AuthState.Initializing
    )
}