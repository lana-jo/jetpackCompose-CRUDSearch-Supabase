package com.lj.crud_supabase.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lj.crud_supabase.presentation.feature.addproduct.AddProductScreen
import com.lj.crud_supabase.presentation.feature.cashier.CashierScreen
//import com.lj.crud_supabase.presentation.feature.cashier.CashierScreen
import com.lj.crud_supabase.presentation.feature.signin.SignInScreen
import com.lj.crud_supabase.presentation.feature.productdetails.ProductDetailsScreen
import com.lj.crud_supabase.presentation.feature.productlist.ProductListScreen
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
    composable(Destination.ProductListDestination.route) {
        ProductListScreen(
            navController = navController
        )
    }

    /**
     * Tujuan untuk layar masuk (sign in).
     */
    composable(Destination.SigninDestination.route) {
        SignInScreen(
            navController = navController
        )
    }

    /**
     * Tujuan untuk layar pendaftaran (sign up).
     */
    composable(Destination.SignUp.route) {
        SignUpScreen(
            navController = navController
        )
    }

    /**composable(Destination.History.route) {
       * // nanti isi screen history
    }*/


    /**
     * Tujuan untuk menambahkan produk baru.
     */
    composable(Destination.AddProductDestination.route) {
        AddProductScreen(
            navController = navController
        )
    }

    composable(Destination.CashierDestination.route) {
        CashierScreen(navController = navController)
    }




    /**
     * Tujuan untuk menampilkan detail produk.
     * Tujuan ini mengambil `productId` sebagai argumen untuk menampilkan detail produk yang benar.
     */
    composable(
        route = "${Destination.ProductDetails.route}/{${Destination.ProductDetails.productId}}",
        arguments = Destination.ProductDetails.arguments
    ) { navBackStackEntry ->
        val productId =
            navBackStackEntry.arguments?.getString(Destination.ProductDetails.productId)
        ProductDetailsScreen(
            productId = productId,
            navController = navController,
        )
    }

}