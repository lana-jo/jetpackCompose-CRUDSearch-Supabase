package com.lj.crud_supabase.presentation.feature.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lj.crud_supabase.presentation.component.TrueButtonStyle
import com.lj.crud_supabase.presentation.utils.InputValidator
import kotlinx.coroutines.launch
import kotlin.text.isNotEmpty

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = { androidx.compose.material.SnackbarHost(snackBarHostState) },
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                backgroundColor = MaterialTheme.colorScheme.primary,
                title = {
                    Text(
                        text = "Sign Up",
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .padding(20.dp)
        ) { val email by viewModel.email.collectAsState(initial = "")
            val password by viewModel.password.collectAsState()

            var showPassword by remember { mutableStateOf(false) }
            val localSoftwareKeyboardController = LocalSoftwareKeyboardController.current

            // Validasi state
            var emailError by remember { mutableStateOf<String?>(null) }
            var passwordError by remember { mutableStateOf<String?>(null) }

            val isEmailValid = InputValidator.validateEmail(email).isValid
            val isPasswordValid = InputValidator.validatePassword(password).isValid
            val isFormValid =
                isEmailValid && isPasswordValid && email.isNotEmpty() && password.isNotEmpty()


            OutlinedTextField(
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                value = email,
                onValueChange = {
                    viewModel.onEmailChange(it)
                    val validation = InputValidator.validateEmail(it)
                    emailError = if (validation.isValid) null else validation.errorMessage
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                shape = RoundedCornerShape(16.dp),
                isError = emailError != null && email.isNotEmpty(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = "Email",
                        tint = if (emailError != null && email.isNotEmpty()) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                },

                supportingText = {
                    if (emailError != null && email.isNotEmpty()) {
                        Text(
                            text = emailError!!,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelSmall
                        )
                    } else if (isEmailValid && email.isNotEmpty()) {
                        Text(
                            "✓ Valid email",
                            color = Color(0xFF4CAF50),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = {
                    viewModel.onPasswordChange(it)
                    val validation = InputValidator.validatePassword(it)
                    passwordError = if (validation.isValid) null else validation.errorMessage
                },
                visualTransformation = if (showPassword) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                isError = !isPasswordValid,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "Password",
                        tint = if (passwordError != null && password.isNotEmpty()) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                },
                supportingText = {
                    if (passwordError != null) {
                        Text(
                            text = passwordError!!,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelSmall
                        )
                    } else if (isPasswordValid && password.isNotEmpty()) {
                        Text(
                            "✓ Valid password",
//                            color = MaterialTheme.colorScheme.primary
                            color = Color(0xFF4CAF50),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                },
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            imageVector = if (showPassword) Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff,
                            contentDescription = "Toggle password visibility"
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                shape = RoundedCornerShape(16.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            TrueButtonStyle(
                onClick = {
                    localSoftwareKeyboardController?.hide()

                    // Validasi final
                    val formValidation = InputValidator.validateLoginForm(email, password)
                    if (formValidation.isValid) {
                        viewModel.onSignUp(
                            email = email.trim(),
                            password = password
                        )
                    } else {
                        // Update error messages
                        emailError = formValidation.emailValidation?.errorMessage
                        passwordError = formValidation.passwordValidation?.errorMessage

                        coroutineScope.launch {
                            snackBarHostState.showSnackbar("Please fix the errors above")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = isFormValid,
                sizeShape = 12
            ) {
                Text(
                    text = if (isFormValid) "Sign Up" else "Fill all fields correctly",
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            /*Button(modifier = modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
                onClick = {
                    localSoftwareKeyboardController?.hide()
                    viewModel.onSignUp()
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar(
                            message = "Create account successfully. Sign in now!",
                            duration = SnackbarDuration.Long
                        )
                    }
                }) {
                Text("Sign up")
            }*/
        }
    }
}