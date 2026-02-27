package com.lj.crud_supabase.presentation.feature.appdrawer

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import kotlinx.coroutines.launch
import com.lj.crud_supabase.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppRoot() {

    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val currentRoute =
        navController.currentBackStackEntryAsState().value?.destination?.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawerContent(
                currentRoute = currentRoute,
                onItemClick = { route ->
                    scope.launch { drawerState.close() }
                    navController.navigate(route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("CRUD Supabase") },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch { drawerState.open() }
                            }
                        ) {
                            Icon(Icons.Default.Menu, null)
                        }
                    }
                )
            }
        ) { padding ->

            NavHost(
                navController = navController,
                startDestination = "product",
                modifier = Modifier.padding(padding)
            ) {
                /*composable("product") { ProductScreen() }
                composable("search") { SearchScreen() }
                composable("create") { CreateProductScreen() }*/
            }
        }
    }
}