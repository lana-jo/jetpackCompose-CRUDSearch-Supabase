/*package com.lj.crud_supabase.presentation.feature.productlist

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lj.crud_supabase.R
import com.lj.crud_supabase.domain.models.AuthState
import com.lj.crud_supabase.presentation.feature.appdrawer.AppDrawerContent
import com.lj.crud_supabase.presentation.feature.appdrawer.AppDrawerScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernTopAppBar(
    authState: AuthState,
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    onSignIn: () -> Unit,
    onSignOut: () -> Unit,
    content: @Composable () -> Unit
) {
    // Shared drawer state and coroutine scope
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Use ModalNavigationDrawer at this top-level so both TopAppBar and AppDrawerContent share state
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            // Reuse your AppDrawerContent directly (no extra ModalDrawerSheet here because AppDrawerContent already has it)
            AppDrawerContent(
                currentRoute = currentRoute,
                onItemClick = { route ->
                    // close drawer then navigate
                    scope.launch { drawerState.close() }
                    onNavigate(route)
                }
            )
        }
    ) {
        // Top app bar + content column
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(
                            onClick = { scope.launch { drawerState.open() } }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = stringResource(R.string.open_app_drawer)
                            )
                        }
                    },
                    title = {
                        Text(
                            text = stringResource(R.string.product_list_text_screen_title),
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    },
                    actions = {
                        when (authState) {
                            AuthState.Authenticated -> {
                                TextButton(onClick = onSignOut) {
                                    Text("Sign Out", color = MaterialTheme.colorScheme.primary)
                                }
                            }

                            AuthState.Unauthenticated -> {
                                TextButton(onClick = onSignIn) {
                                    Text("Sign In", color = MaterialTheme.colorScheme.primary)
                                }
                            }

                            AuthState.Initializing -> {
                                // optional loading
                            }
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
            },
            content = { innerPadding ->
                // Pass through content (make sure your content applies innerPadding if needed)
                Surface(modifier = androidx.compose.ui.Modifier.padding(innerPadding)) {
                    content()
                }
            }
        )
    }
}*/

package com.lj.crud_supabase.presentation.feature.productlist

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lj.crud_supabase.R
import com.lj.crud_supabase.domain.models.AuthState
import com.lj.crud_supabase.presentation.feature.appdrawer.AppDrawerScreen
import com.lj.crud_supabase.presentation.navigation.Destination


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernTopAppBar(
    authState: AuthState,
    onSignIn: () -> Unit,
    onSignOut: () -> Unit
) {/*
var currentDestination by rememberSaveable {
        mutableStateOf(Destination.ProductListDestination.route)
    }
*/
//    CenterAlignedTopAppBar(
    TopAppBar(
 /*IconButton(onClick = {
//                 AppDrawerScreen(
//                     Destination.ProductListDestination.route,
//                     onNavigate = ,
//                 ) { }
            }) {
                Icon(Icons.Default.Menu, null)
            },
            Spacer(modifier = Modifier.width(16.dp)),
*/
        title = {
            Text(
                text = stringResource(R.string.product_list_text_screen_title),
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
            )
        },
        actions = {
            when (authState) {
                AuthState.Authenticated -> {
                    TextButton(onClick = onSignOut) {
                        Text("Sign Out", color = MaterialTheme.colorScheme.primary)
                    }
                }

                AuthState.Unauthenticated -> {
                    TextButton(onClick = onSignIn) {
                        Text("Sign In", color = MaterialTheme.colorScheme.primary)
                    }
                }

                AuthState.Initializing -> {
                    // You can show a loading indicator here if needed
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
        )
    )
}
