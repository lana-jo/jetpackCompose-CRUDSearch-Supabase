package com.lj.crud_supabase.presentation.feature.auth.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lj.crud_supabase.data.repository.user.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepo: AuthRepository
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: Flow<String> = _email

    private val _password = MutableStateFlow("")
    val password = _password

    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onSignIn() {
        viewModelScope.launch {
            authRepo.signIn(
                email = _email.value,
                password = _password.value
            )
        }
    }

    fun onGoogleSignIn() {
        viewModelScope.launch {
            authRepo.signInWithGoogle()
        }
    }

}