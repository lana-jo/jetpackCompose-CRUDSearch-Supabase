package com.lj.crud_supabase.presentation.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument


/**
 * Mendefinisikan kontrak untuk tujuan navigasi dalam aplikasi.
 * Setiap tujuan memiliki rute (route) unik dan judul (title).
 */
interface Destination {
    /**
     * Rute unik yang mengidentifikasi tujuan.
     */
    val route: String

    /**
     * Judul yang akan ditampilkan untuk tujuan ini.
     */
    val title: String
}


/**
 * Tujuan untuk menampilkan daftar produk.
 */
object ProductListDestination : Destination {
    override val route = "product_list"
    override val title = "Product List"
}

/**
 * Tujuan untuk menampilkan detail suatu produk.
 */
object ProductDetailsDestination : Destination {
    override val route = "product_details"
    override val title = "Product Details"

    /**
     * Kunci untuk argumen ID produk.
     */
    const val productId = "product_id"

    /**
     * Daftar argumen yang diperlukan untuk tujuan ini, yaitu `productId`.
     */
    val arguments = listOf(navArgument(name = productId) {
        type = NavType.StringType
    })

    /**
     * Membuat rute navigasi lengkap dengan menyertakan ID produk.
     *
     * @param productId ID produk yang akan disertakan dalam rute.
     * @return Rute lengkap dengan parameter.
     */
    fun createRouteWithParam(productId: String) = "$route/${productId}"
}

/**
 * Tujuan untuk layar penambahan produk baru.
 */
object AddProductDestination : Destination {
    override val route = "add_product"
    override val title = "Add Product"
}

/**
 * Tujuan untuk layar otentikasi (masuk).
 */
object AuthenticationDestination: Destination {
    override val route = "authentication"
    override val title = "Authentication"
}

/**
 * Tujuan untuk layar pendaftaran (sign up) pengguna baru.
 */
object SignUpDestination: Destination {
    override val route = "signup"
    override val title = "Sign Up"
}