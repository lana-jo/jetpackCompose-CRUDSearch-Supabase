package com.lj.crud_supabase.presentation.feature.appdrawer

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/*import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector*/


@Composable
fun DrawerItem(
    label: String,
    route: String,
    currentRoute: String?,
    onClick: (String) -> Unit
) {
    NavigationDrawerItem(
        label = { Text(text = label) },
        selected = currentRoute == route,
        onClick = { onClick(route) },
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
    )
}