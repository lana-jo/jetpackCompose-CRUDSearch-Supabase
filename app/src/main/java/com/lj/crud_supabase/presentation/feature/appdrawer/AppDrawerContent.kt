package com.lj.crud_supabase.presentation.feature.appdrawer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppDrawerContent(
    currentRoute: String?,
    onItemClick: (String) -> Unit
) {
    ModalDrawerSheet {

        Spacer(modifier = Modifier.height(16.dp))

        // Header
        Text(
            text = "ayam geprek",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Divider()

        DrawerItem(
            label = "Kasir",
            route = "cashier",
            currentRoute = currentRoute,
            onClick = onItemClick
        )

        DrawerItem(
            label = "Riwayat Transaksi",
            route = "history",
            currentRoute = currentRoute,
            onClick = onItemClick
        )

        DrawerItem(
            label = "Kelola Produk",
            route = "product",
            currentRoute = currentRoute,
            onClick = onItemClick
        )

        DrawerItem(
            label = "Pelanggan",
            route = "customer",
            currentRoute = currentRoute,
            onClick = onItemClick
        )

        Spacer(modifier = Modifier.weight(1f))

        Divider()

        Text(
            text = "APP version v1.0.0",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodySmall
        )
    }
}