package com.lj.crud_supabase.presentation.utils

import java.text.NumberFormat
import java.util.Locale

/**
 * Format harga ke format mata uang Indonesia (IDR)
 * 
 * Contoh:
 * 100000.0 -> "Rp 100.000"
 * 1500000.0 -> "Rp 1.500.000"
 */
fun formatPriceToIDR(price: Double): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    return formatter.format(price)
}

/**
 * Format harga ke format mata uang Indonesia tanpa simbol (hanya angka)
 * 
 * Contoh:
 * 100000.0 -> "100.000"
 * 1500000.0 -> "1.500.000"
 */
fun formatPriceIDRSimple(price: Double): String {
    val formatter = NumberFormat.getInstance(Locale("id", "ID"))
    return formatter.format(price.toLong())
}

/**
 * Parse string input user menjadi Double
 * 
 * Contoh:
 * "100000" -> 100000.0
 * "1500000" -> 1500000.0
 */
fun parseUserPriceInput(input: String): Double {
    return input.replace("[^0-9.]".toRegex(), "").toDoubleOrNull() ?: 0.0
}