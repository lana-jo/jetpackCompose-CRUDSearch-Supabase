package com.lj.crud_supabase.presentation.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.launch
import com.lj.crud_supabase.domain.models.AuthState
import com.lj.crud_supabase.presentation.feature.appdrawer.AppDrawerContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScaffold(
    currentRoute: String?,
    authState: AuthState,
    onNavigate: (String) -> Unit,
    onSignIn: () -> Unit,
    onSignOut: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawerContent(
                currentRoute = currentRoute,
                onItemClick = { route ->
                    scope.launch { drawerState.close() }
                    onNavigate(route)
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch { drawerState.open() }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    },
                    title = {
                        Text(
                            text = currentRoute ?: "",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    },
                    actions = {
                        when (authState) {
                            AuthState.Authenticated -> {
                                TextButton(onClick = onSignOut) {
                                    Text("Sign Out")
                                }
                            }
                            AuthState.Unauthenticated -> {
                                TextButton(onClick = onSignIn) {
                                    Text("Sign In")
                                }
                            }
                            AuthState.Initializing -> {}
                        }
                    }
                )
            }
        ) { padding ->
            content(padding)
        }
    }
}