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

fun NavGraphBuilder.navRegistration(navController: NavController) {
    composable(ProductListDestination.route) {
        ProductListScreen(
            navController = navController
        )
    }

    composable(AuthenticationDestination.route) {
        SignInScreen(
            navController = navController
        )
    }

    composable(SignUpDestination.route) {
        SignUpScreen(
            navController = navController
        )
    }

    composable(AddProductDestination.route) {
        AddProductScreen(
            navController = navController
        )
    }

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