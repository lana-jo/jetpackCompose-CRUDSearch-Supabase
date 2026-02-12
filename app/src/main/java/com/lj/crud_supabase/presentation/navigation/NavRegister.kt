package com.lj.crud_supabase.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lj.crud_supabase.presentation.feature.addproduct.AddProductScreen
import com.lj.crud_supabase.presentation.feature.signin.SignInScreen
import com.lj.crud_supabase.presentation.feature.productdetails.ProductDetailsScreen
import com.lj.crud_supabase.presentation.feature.productlist.ProductListScreen
import com.lj.crud_supabase.presentation.feature.signin.SignInSuccessScreen
import com.lj.crud_supabase.presentation.feature.signup.SignUpScreen

/**
 * Mendaftarkan semua tujuan navigasi untuk aplikasi.
 *
 * @param navController Pengontrol navigasi untuk mengelola navigasi antar layar.
 */
fun NavGraphBuilder.navRegistration(navController: NavController) {

    /**
     * Tujuan untuk menampilkan daftar produk.
     */
    composable(ProductListDestination.route) {
        ProductListScreen(
            navController = navController
        )
    }

    /**
     * Tujuan untuk layar masuk (sign in).
     */
    composable(AuthenticationDestination.route) {
        SignInScreen(
            navController = navController
        )
    }

    /**
     * Tujuan untuk layar pendaftaran (sign up).
     */
    composable(SignUpDestination.route) {
        SignUpScreen(
            navController = navController
        )
    }

    /**
     * Tujuan untuk menambahkan produk baru.
     */
    composable(AddProductDestination.route) {
        AddProductScreen(
            navController = navController
        )
    }

    /**
     * Tujuan untuk menampilkan detail produk.
     * Tujuan ini mengambil `productId` sebagai argumen untuk menampilkan detail produk yang benar.
     */
    composable(
        route = "${ProductDetailsDestination.route}/{${ProductDetailsDestination.productId}}",
        arguments = ProductDetailsDestination.arguments
    ) { navBackStackEntry ->
        val productId =
            navBackStackEntry.arguments?.getString(ProductDetailsDestination.productId)
        ProductDetailsScreen(
            productId = productId,
            navController = navController,
        )
    }

}