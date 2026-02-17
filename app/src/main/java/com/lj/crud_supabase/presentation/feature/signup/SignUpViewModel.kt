package com.lj.crud_supabase.presentation.feature.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lj.crud_supabase.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _email = MutableStateFlow<String>("")
    val email: Flow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: Flow<String> = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: Flow<String> = _confirmPassword.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: Flow<Boolean> = _isLoading.asStateFlow()

    private val _message = MutableStateFlow("")
    val message: Flow<String> = _message.asStateFlow()

    private val _signUpSuccess = MutableStateFlow(false)
    val signUpSuccess: Flow<Boolean> = _signUpSuccess.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: Flow<String?> = _errorMessage.asStateFlow()

    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _confirmPassword.value = confirmPassword
    }

    fun onSignUp(email: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            try {
                // Validasi
                if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    _errorMessage.value = "Please fill all fields"
                    return@launch
                }

                if (password != confirmPassword) {
                    _errorMessage.value = "Passwords do not match"
                    return@launch
                }

                if (password.length < 6) {
                    _errorMessage.value = "Password must be at least 6 characters"
                    return@launch
                }

                _isLoading.value = true
                _errorMessage.value = null

                Timber.d("Starting sign up for email: $email")

                val result = signUpUseCase.execute(
                    SignUpUseCase.Input(
                        email = email.trim(),
                        password = password
                    )

                )

                when (result) {
                    is SignUpUseCase.Output.Success -> {
                        Timber.d("Sign up successful!")
                        _signUpSuccess.value = true
                        _message.value = "Account created successfully! Please check your email to verify."
                        // Clear form
                        _email.value = ""
                        _password.value = ""
                        _confirmPassword.value = ""
                    }
                    is SignUpUseCase.Output.Failure -> {
                        Timber.e("Sign up failed")
                        _errorMessage.value = "Sign up failed. Please try again."
                        _signUpSuccess.value = false
                    }
                }
            } catch (e: Exception) {
                Timber.e(e, "Error during sign up")
                _errorMessage.value = e.message ?: "An error occurred during sign up"
                _signUpSuccess.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }

    fun resetSignUpSuccess() {
        _signUpSuccess.value = false
    }
}


/*
package com.lj.crud_supabase.presentation.feature.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lj.crud_supabase.data.repository.AuthRepo
import com.lj.crud_supabase.domain.model.SignUpUiState
import com.lj.crud_supabase.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _email = MutableStateFlow<String>("")
    val email: Flow<String> = _email

    private val _password = MutableStateFlow("")
    val password = _password

    private val _message = MutableStateFlow("")
    val message = _message

    private val _uiState = MutableStateFlow<SignUpUiState>(SignUpUiState.Idle)
    val uiState: StateFlow<SignUpUiState> = _uiState



    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onSignUp(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = SignUpUiState.Loading

           */
/* val result = signUpUseCase.execute(
                SignUpUseCase.Input(
                    email = _email.value,
                    password = _password.value
                )
            )
            when (result) {
                is SignUpUseCase.Output.Success -> {
                    _message.emit("Account created successfully!")
                }
                else -> {
                    _message.emit("Create account failed !")

                }
            }*//*

            val result = AuthRepo.signUp(email, password)
            _uiState.value = result.fold(
                onSuccess = { SignUpUiState.Success },
                onFailure = { SignUpUiState.Error(it.message ?: "Error") }
            )
        }

    }
}*/
