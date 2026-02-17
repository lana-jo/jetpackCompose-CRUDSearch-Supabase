package com.lj.crud_supabase.presentation.feature.signin

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.PaddingValues
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
import com.lj.crud_supabase.presentation.component.TrueButtonStyle
import com.lj.crud_supabase.presentation.utils.InputValidator
import kotlinx.coroutines.launch
import timber.log.Timber

@SuppressLint("TimberArgCount")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel(),
    onSignedIn: () -> Unit = {
        //navigate to home by caller
    }
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val email by viewModel.email.collectAsState(initial = "")
    val password by viewModel.password.collectAsState(initial = "")
    val isLoading by viewModel.isLoading.collectAsState(initial = false)
    val errorMessage by viewModel.errorMessage.collectAsState(initial = null)
//    val signInSuccess by viewModel.signInSuccess.collectAsState(initial = null)
    val signInSuccess by viewModel.signInSuccess.collectAsState(initial = false)
    val message by viewModel.message.collectAsState(initial = "")

    var showPassword by remember { mutableStateOf(false) }
    val localSoftwareKeyboardController = LocalSoftwareKeyboardController.current

    // Validation state
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    // Show error in snackbar
    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            coroutineScope.launch {
                snackBarHostState.showSnackbar(errorMessage!!)
                viewModel.clearError()
            }
        }
    }

    // Handle successful sign in
    LaunchedEffect(signInSuccess) {
        if (signInSuccess) {

            coroutineScope.launch {
                snackBarHostState.showSnackbar(
                    message = message.ifEmpty { "Signed in successfully" },
                    duration = SnackbarDuration.Short
                )
                // Notify caller to navigate to home
                onSignedIn()
                viewModel.resetSignInSuccess()
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Sign In",
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
                        text = "Welcome Back",
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
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Sign In Button
                    TrueButtonStyle(
                        onClick = {
                            localSoftwareKeyboardController?.hide()

                            // Validate form using same validator
                            val formValidation = InputValidator.validateLoginForm(email, password)
                            emailError = formValidation.emailValidation?.errorMessage
                            passwordError = formValidation.passwordValidation?.errorMessage

                            if (emailError == null && passwordError == null) {
                                viewModel.onSignIn(
                                    email = email.trim(),
                                    password = password
                                )
                                Timber.d("signin attempt", email)
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
                            text = if (isLoading) "Signing In..." else "Sign In",
                            modifier = Modifier.padding(8.dp),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Sign Up Link
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Don't have an account? ")
                        Spacer(modifier = Modifier.width(4.dp))

                        TextButton(
                            onClick = { navController.navigate("signup") },
                            contentPadding = PaddingValues(0.dp) // 🔥 ini kuncinya

                        ) {
                            Text(
                                "Sign Up",
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Optional: Forgot password link
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(onClick = {
                        //navigate to forgot password screen
                        }) {
                            Text(
                                "Forgot password?",
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}

/*package com.lj.crud_supabase.presentation.feature.signin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import com.lj.crud_supabase.presentation.component.TrueButtonStyle
import com.lj.crud_supabase.presentation.navigation.SignUpDestination
import com.lj.crud_supabase.presentation.utils.InputValidator
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val isLoading by viewModel.isLoading.collectAsState(initial = false)
    val errorMessage by viewModel.errorMessage.collectAsState(initial = null)
    val signUpSuccess by viewModel.signInSuccess.collectAsState(initial = false)
    val message by viewModel.message.collectAsState(initial = "")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Sign In",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                )
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val email by viewModel.email.collectAsState(initial = "")
            val password by viewModel.password.collectAsState(initial = "")
            val localSoftwareKeyboardController = LocalSoftwareKeyboardController.current
            var showPassword by remember { mutableStateOf(false) }

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
                        viewModel.onSignIn(
                            email = email.trim(),
                            password = password
                        )
                        coroutineScope.launch {
                            snackBarHostState.showSnackbar("Sign in successfully!")
                        }
//                        navController.navigau

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
                    text = if (isFormValid) "Sign In" else "Fill all fields correctly",
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            TextButton(onClick = {
//                Handle Google Sign - In
            }) {
                Text("Or sign in with Google")
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { navController.navigate(SignUpDestination.route) },
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Don't have an account? Sign up", modifier = Modifier.padding(8.dp))
            }
        }
    }
}*/
//button for login
/* Button(
     modifier = Modifier.fillMaxWidth(),
     onClick = {
         localSoftwareKeyboardController?.hide()
         val formValidation = InputValidator.validateLoginForm(email, password)
         if (formValidation.isValid) {
             viewModel.onSignIn(
                 email = email.trim(),
                 password = password
             )
             coroutineScope.launch {
                 snackBarHostState.showSnackbar("Sign in successfully!")
                 navController.navigateUp()
             }
         } else {
             // Update error messages
             emailError = formValidation.emailValidation?.errorMessage
             passwordError = formValidation.passwordValidation?.errorMessage

             coroutineScope.launch {
                 snackBarHostState.showSnackbar("Please fix the errors above")
             }
         }

     },
     colors = ButtonDefaults.buttonColors(
         containerColor = Color.Green,
         contentColor = Color.Black,
         disabledContainerColor = Color.Black.copy(alpha = 0.5f),
         disabledContentColor = Color.DarkGray.copy(alpha = 0.5f)
     ),
     enabled = isFormValid,
     shape = RoundedCornerShape(16.dp)
 ) {
     Text("Sign in", modifier = Modifier.padding(8.dp))
 }*/
