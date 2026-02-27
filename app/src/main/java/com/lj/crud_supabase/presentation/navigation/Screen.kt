package com.lj.crud_supabase.presentation.navigation

sealed class Screen(val route: String) {
    data object KasirHome : Screen("kasir_home")
    data object Transaksi : Screen("transaksi")       // daftar transaksi / keranjang
    data object Produk : Screen("produk")             // cari & tambah ke keranjang
    data object Profile : Screen("profile")           // info kasir / logout
    // tambah lagi sesuai kebutuhan, misal LaporanHarian, dll.
}
