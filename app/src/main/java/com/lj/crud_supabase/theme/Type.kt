package com.lj.crud_supabase.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Mendefinisikan set gaya tipografi Material untuk digunakan dalam aplikasi.
 * Gaya-gaya ini dapat diterapkan ke composable teks untuk memastikan konsistensi
 * di seluruh UI aplikasi.
 *
 * Contoh:
 * ```
 * Text("Hello World", style = MaterialTheme.typography.bodyLarge)
 * ```
 */
val Typography = Typography(
    /**
     * Gaya default untuk teks isi yang besar.
     * Digunakan untuk teks yang lebih panjang dan blok teks.
     */
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Gaya teks default lain yang dapat diganti
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)