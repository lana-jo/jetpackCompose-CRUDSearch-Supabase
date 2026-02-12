package com.lj.crud_supabase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lj.crud_supabase.presentation.navigation.ProductListDestination
import com.lj.crud_supabase.presentation.navigation.navRegistration
import com.lj.crud_supabase.theme.ProductTheme
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.SupabaseClient
import javax.inject.Inject

/**
 * Aktivitas utama aplikasi.
 *
 * Aktivitas ini adalah titik masuk aplikasi dan menjadi host bagi UI Jetpack Compose.
 * Aktivitas ini menyiapkan grafik navigasi dan tema untuk aplikasi.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /**
     * Klien Supabase untuk berinteraksi dengan backend Supabase.
     */
    @Inject
    lateinit var supabaseClient: SupabaseClient

    /**
     * Dipanggil saat aktivitas pertama kali dibuat.
     *
     * Di sinilah Anda harus melakukan semua penyiapan statis normal Anda: membuat tampilan, mengikat data ke daftar, dll.
     * Metode ini juga memberi Anda Bundle yang berisi status aktivitas yang sebelumnya dibekukan, jika ada.
     *
     * @param savedInstanceState Jika aktivitas diinisialisasi ulang setelah sebelumnya dimatikan maka Bundle ini berisi data yang terakhir diberikan dalam onSaveInstanceState(Bundle). Catatan: Jika tidak, nilainya null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProductTheme {
                // Wadah permukaan yang menggunakan warna 'latar belakang' dari tema
                val navController = rememberNavController()
                val currentBackStack by navController.currentBackStackEntryAsState()
                val currentDestination = currentBackStack?.destination
                Scaffold { innerPadding ->
                    NavHost(
                        navController,
                        startDestination = ProductListDestination.route,
                        Modifier.padding(innerPadding)
                    ) {
                        navRegistration(navController)
                    }
                }
            }
        }
    }
}