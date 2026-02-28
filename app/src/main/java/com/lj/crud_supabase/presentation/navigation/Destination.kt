package com.lj.crud_supabase.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.lj.crud_supabase.domain.models.Product
import com.lj.crud_supabase.presentation.feature.productlist.ProductListScreen

sealed class Destination(
    val route: String,
    val title: String,
    val icon: ImageVector? =null,
    val showInDrawer: Boolean = true
) {

    object ProductListDestination : Destination(
        route = "product",
        title = "Kelola Produk",
        icon = Icons.Default.Inventory
    )

    object CashierDestination : Destination(
        route = "cashier",
        title = "Kasir",
        icon = Icons.Default.PointOfSale
    )

    object HistoryDestination : Destination(
        route = "history",
        title = "Riwayat Transaksi",
        icon = Icons.Default.ReceiptLong
    )

    object CustomerDestination : Destination(
        route = "customer",
        title = "Pelanggan",
        icon = Icons.Default.People
    )

    object SigninDestination : Destination(
        route = "signin",
        title = "Sign In",
//        icon = Icons.Default.Login,
        showInDrawer = false
    )

    data object AddProductDestination : Destination(
        route = "add_product",
        title = "Add Product",
//        icon = Icons.Default.Add,
        showInDrawer = false
    )

    data object Authentication : Destination(
        route = "authentication",
        title = "Authentication",
//        icon = Icons.Default.Login,
        showInDrawer = false
    )

    data object SignUp : Destination(
        route = "signup",
        title = "Sign Up",
//        icon = Icons.Default.PersonAdd,
        showInDrawer = false
    )

    data object ProductDetails : Destination(
//        route = "product_details",
        route = "product_details/{product_id}",
        title = "Product Details",
//        icon = Icons.Default.Info,
        showInDrawer = false
    ) {
        const val productId = "product_id"

        val arguments = listOf(
            navArgument(name = productId) {
                type = NavType.StringType
            }
        )

        fun createRoute(productId: String) =
            "$route/$productId"
    }


    companion object {
        val all = listOf(
            ProductListDestination,
            CashierDestination,
            HistoryDestination,
            CustomerDestination,
            SigninDestination,
            AddProductDestination,
            Authentication,
            SignUp,
            ProductDetails
        )

        fun drawerDestinations(): List<Destination> {
            return listOf(
                ProductListDestination,
                CashierDestination,
                HistoryDestination,
                CustomerDestination,
            )
        }

        fun fromRoute(route: String?): Destination {
            return when (route) {
                ProductListDestination.route -> ProductListDestination
                CashierDestination.route -> CashierDestination
                HistoryDestination.route -> HistoryDestination
                CustomerDestination.route -> CustomerDestination
                SigninDestination.route -> SigninDestination
                AddProductDestination.route -> AddProductDestination
                Authentication.route -> Authentication
                SignUp.route -> SignUp
                ProductDetails.route -> ProductDetails
                else -> ProductListDestination // Default to ProductList
            }
        }
        /*fun drawerDestinations(): List<Destination> {
            return all.filter { it.showInDrawer }
        }

        fun fromRoute(route: String?): Destination {
            return all.firstOrNull { it.route == route } ?: ProductListDestination
        }*/
    }
}
