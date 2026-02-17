package com.lj.crud_supabase.presentation.feature.signup

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import com.lj.crud_supabase.MainActivity
import com.lj.crud_supabase.presentation.component.TrueButtonStyle
import com.lj.crud_supabase.presentation.utils.InputValidator
import kotlinx.coroutines.launch
import timber.log.Timber

@SuppressLint("TimberArgCount")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val email by viewModel.email.collectAsState(initial = "")
    val password by viewModel.password.collectAsState(initial = "")
    val confirmPassword by viewModel.confirmPassword.collectAsState(initial = "")
    val isLoading by viewModel.isLoading.collectAsState(initial = false)
    val errorMessage by viewModel.errorMessage.collectAsState(initial = null)
    val signUpSuccess by viewModel.signUpSuccess.collectAsState(initial = false)
    val message by viewModel.message.collectAsState(initial = "")

    var showPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }
    val localSoftwareKeyboardController = LocalSoftwareKeyboardController.current

    // Validasi state
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }

    // Show error in snackbar
    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            coroutineScope.launch {
                snackBarHostState.showSnackbar(errorMessage!!)
                viewModel.clearError()
            }
        }
    }

    // Handle successful sign up
    LaunchedEffect(signUpSuccess) {
        if (signUpSuccess) {
            coroutineScope.launch {
                snackBarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Long
                )
                // Navigate back to sign in
                navController.navigateUp()
                viewModel.resetSignUpSuccess()
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Sign Up",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = if (isLoading) Alignment.Center else Alignment.TopCenter
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
            } else {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Title
                    Text(
                        text = "Create New Account",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Email Field
                    OutlinedTextField(
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth(),
                        value = email,
                        onValueChange = {
                            viewModel.onEmailChange(it)
                            emailError = null
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Email,
                                contentDescription = "Email"
                            )
                        },
                        shape = RoundedCornerShape(16.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        isError = emailError != null,
                        supportingText = {
                            if (emailError != null) {
                                Text(emailError!!, color = MaterialTheme.colorScheme.error)
                            }
                        }
                    )

                    // Password Field
                    OutlinedTextField(
                        label = { Text("Password") },
                        modifier = Modifier.fillMaxWidth(),
                        value = password,
                        onValueChange = {
                            viewModel.onPasswordChange(it)
                            passwordError = null
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Lock,
                                contentDescription = "Password"
                            )
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
                        visualTransformation = if (showPassword) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        shape = RoundedCornerShape(16.dp),
                        isError = passwordError != null,
                        supportingText = {
                            if (passwordError != null) {
                                Text(passwordError!!, color = MaterialTheme.colorScheme.error)
                            } else {
                                Text("At least 6 characters")
                            }
                        }
                    )

                    // Confirm Password Field
                    OutlinedTextField(
                        label = { Text("Confirm Password") },
                        modifier = Modifier.fillMaxWidth(),
                        value = confirmPassword,
                        onValueChange = {
                            viewModel.onConfirmPasswordChange(it)
                            confirmPasswordError = null
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Lock,
                                contentDescription = "Confirm Password"
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = { showConfirmPassword = !showConfirmPassword }) {
                                Icon(
                                    imageVector = if (showConfirmPassword) Icons.Filled.Visibility
                                    else Icons.Filled.VisibilityOff,
                                    contentDescription = "Toggle password visibility"
                                )
                            }
                        },
                        visualTransformation = if (showConfirmPassword) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        shape = RoundedCornerShape(16.dp),
                        isError = confirmPasswordError != null,
                        supportingText = {
                            if (confirmPasswordError != null) {
                                Text(confirmPasswordError!!, color = MaterialTheme.colorScheme.error)
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Sign Up Button
                    TrueButtonStyle(
                        onClick = {
                            localSoftwareKeyboardController?.hide()

                            // Validasi form
                            val formValidation = InputValidator.validateLoginForm(email, password)
                            emailError = formValidation.emailValidation?.errorMessage
                            passwordError = formValidation.passwordValidation?.errorMessage

                            if (password != confirmPassword) {
                                confirmPasswordError = "Passwords do not match"
                            }

                            if (emailError == null && passwordError == null && confirmPasswordError == null) {
                                viewModel.onSignUp(
                                    email = email.trim(),
                                    password = password,
                                    confirmPassword = confirmPassword
                                )
                                Timber.d("signup succes", email, password)
                            } else {
                                coroutineScope.launch {
                                    snackBarHostState.showSnackbar("Please fix the errors above")
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        enabled = !isLoading,
                        sizeShape = 12
                    ) {
                        Text(
                            text = if (isLoading) "Creating Account..." else "Sign Up",
                            modifier = Modifier.padding(8.dp),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Sign In Link
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Already have an account?",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        TextButton(
                            onClick = { navController.navigateUp() },
                            contentPadding = PaddingValues(0.dp) // 🔥 ini kuncinya
                        ) {
                            Text(
                                text = "Sign In",
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                }
            }
        }
    }
}

/*
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
import androidx.compose.runtime.LaunchedEffect
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
import com.lj.crud_supabase.domain.model.SignUpUiState
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
//    val message by viewModel.message.collectAsState()
//    val uiState by viewModel.uiState.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    */
/*LaunchedEffect(uiState) {
        when (uiState) {
            is SignUpUiState.Success -> {
                snackBarHostState.showSnackbar("Account created successfully!")
                navController.navigate("signin") {
                    popUpTo("signup") { inclusive = true }
                }
            }

            is SignUpUiState.Error -> {
                snackBarHostState.showSnackbar(
                    (uiState as SignUpUiState.Error).message
                )
            }

            else -> {}
        }
    }*//*

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
                    fontSize = 16.sp,

                )
            }
            */
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
            }*//*

        }
    }
}*/
