package com.lj.crud_supabase.presentation.feature.appdrawer

/*
import androidx.compose.material.DrawerValue
import androidx.compose.material.rememberDrawerState
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
*/


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun AppDrawerScreen(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    content: @Composable () -> Unit
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
        content()
    }
}