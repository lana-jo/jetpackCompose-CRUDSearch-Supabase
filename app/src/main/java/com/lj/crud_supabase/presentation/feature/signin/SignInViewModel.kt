/*
package com.lj.crud_supabase.presentation.feature.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lj.crud_supabase.domain.usecase.SignInUseCase
import com.lj.crud_supabase.domain.usecase.SignInWithGoogleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
) : ViewModel() {


    */
/*private val _email = MutableStateFlow<String>("")
    val email: Flow<String> = _email

    private val _password = MutableStateFlow("")
    val password = _password

    private val _message = MutableStateFlow("")
    val message = _message
    *//*

    private val _email = MutableStateFlow<String>("")

    val email: Flow<String> = _email.asStateFlow()
    private val _password = MutableStateFlow("")

    val password: Flow<String> = _password.asStateFlow()
    private val _isLoading = MutableStateFlow(false)

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: Flow<String?> = _errorMessage.asStateFlow()

    val isLoading: Flow<Boolean> = _isLoading.asStateFlow()

    private val _message = MutableStateFlow("")
    val message: Flow<String> = _message.asStateFlow()

    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onGoogleSignIn() {
        viewModelScope.launch {
            signInWithGoogleUseCase.execute(SignInWithGoogleUseCase.Input())
        }
    }

    fun onSignIn(email: String, password: String) {
        viewModelScope.launch {
            val result = signInUseCase.execute(
                SignInUseCase.Input(
                    email = email,
                    password = password
                )
            )
            Timber.d("Result SignInViewModel onSignIn: $result")
            when (result) {
                is SignInUseCase.Output.Success -> {
                    _message.value ="Login successfully !"
                }

                else -> {
                    _message.value="Login failed !"
                }
            }
        }
    }


}*/

package com.lj.crud_supabase.presentation.feature.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lj.crud_supabase.domain.usecase.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    private val _email = MutableStateFlow<String>("")
    val email: Flow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: Flow<String> = _password.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: Flow<Boolean> = _isLoading.asStateFlow()

    private val _message = MutableStateFlow("")
    val message: Flow<String> = _message.asStateFlow()

    private val _signInSuccess = MutableStateFlow(false)
    val signInSuccess: Flow<Boolean> = _signInSuccess.asStateFlow()
    /*private val _signInSuccess = MutableStateFlow<SignInUseCase.Output?>(null)
    val signInSuccess: Flow<SignInUseCase.Output?> = _signInSuccess.asStateFlow()*/

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: Flow<String?> = _errorMessage.asStateFlow()

    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onSignIn(email: String, password: String) {
        viewModelScope.launch {
            try {
                // Validasi sederhana
                if (email.isEmpty() || password.isEmpty()) {
                    _errorMessage.value = "Please fill all fields"
                    return@launch
                }
//                if (email === )

                if (password.length < 6) {
                    _errorMessage.value = "Password must be at least 6 characters"
                    return@launch
                }

                _isLoading.value = true
                _errorMessage.value = null

                Timber.d("Starting sign in for email: $email")

                val result = signInUseCase.execute(
                    SignInUseCase.Input(
                        email = email.trim(),
                        password = password
                    )
                )

                when (result) {
                    is SignInUseCase.Output.Success -> {
                        Timber.d("Sign in successful!")
                        _signInSuccess.value = true
                        _message.value = "Signed in successfully"
                        // Optionally clear form
                        _email.value = ""
                        _password.value = ""
                    }
                    is SignInUseCase.Output.Failure -> {
                        Timber.e("Sign in failed")
                        _errorMessage.value = "Sign in failed. Please try again."
                        _signInSuccess.value = false
                    }
                }
            } catch (e: Exception) {
                Timber.e(e, "Error during sign in")
                _errorMessage.value = e.message ?: "An error occurred during sign in"
                _signInSuccess.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }

    fun resetSignInSuccess() {
        _signInSuccess.value = false
    }
}