package com.lj.crud_supabase.presentation.feature.cashier

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.lj.crud_supabase.presentation.navigation.Screen

@Composable
fun CashierBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == Screen.KasirHome.route,
            onClick = { onNavigate(Screen.KasirHome.route) },
            icon = { Icon(Icons.Default.Home, "Home") },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = currentRoute == Screen.Produk.route,
            onClick = { onNavigate(Screen.Produk.route) },
            icon = { Icon(Icons.Default.Search, "Produk") },
            label = { Text("Produk") }
        )
        NavigationBarItem(
            selected = currentRoute == Screen.Transaksi.route,
            onClick = { onNavigate(Screen.Transaksi.route) },
            icon = { Icon(Icons.Default.ShoppingCart, "Transaksi") },
            label = { Text("Keranjang") }
        )
        NavigationBarItem(
            selected = currentRoute == Screen.Profile.route,
            onClick = { onNavigate(Screen.Profile.route) },
            icon = { Icon(Icons.Default.Person, "Profile") },
            label = { Text("Profile") }
        )
    }
}