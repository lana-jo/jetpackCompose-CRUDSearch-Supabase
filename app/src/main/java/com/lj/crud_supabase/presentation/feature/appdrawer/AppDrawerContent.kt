package com.lj.crud_supabase.presentation.feature.appdrawer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lj.crud_supabase.presentation.navigation.Destination

@Composable
fun AppDrawerContent(
    currentRoute: String?,
    onItemClick: (String) -> Unit
) {

    ModalDrawerSheet {

        Spacer(modifier = Modifier.height(24.dp))

        // 🔥 LOGO HEADER
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {

            Icon(
                imageVector = Icons.Default.Store,
                contentDescription = "App Logo",
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Ayam Geprek POS",
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()

        // 🔥 AUTO GENERATE DRAWER ITEMS
        Destination.drawerDestinations().forEach { destination ->
            DrawerItem(
                label = destination.title,
                icon = destination.icon,
                route = destination.route,
                currentRoute = currentRoute,
                onClick = onItemClick
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        HorizontalDivider()

        Text(
            text = "APP version v1.0.0",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodySmall
        )
    }
}