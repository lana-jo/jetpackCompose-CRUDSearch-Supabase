package com.lj.crud_supabase.presentation.feature.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lj.crud_supabase.data.repository.user.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel(){
    private val _email = MutableStateFlow("")
    val email: Flow<String> = _email

    private val _password = MutableStateFlow("")
    val password: Flow<String> = _password

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: Flow<String> = _confirmPassword

    private val _isLoading = MutableStateFlow(false)
    val isLoading: Flow<Boolean> = _isLoading

    fun onEmailChange(email: String) {
        _email.value = email
    }
    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onSignUp() {
        viewModelScope.launch {
            authRepository.signUp(
                email = _email.value,
                password = _password.value
            )
        }
    }
}