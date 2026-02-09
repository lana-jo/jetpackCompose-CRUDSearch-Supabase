package com.lj.crud_supabase

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lj.crud_supabase.navigate.AddProductDestination
import com.lj.crud_supabase.navigate.AuthenticationDestination
import com.lj.crud_supabase.navigate.ProductDetailsDestination
import com.lj.crud_supabase.navigate.ProductListDestination
import com.lj.crud_supabase.navigate.SignUpDestination
import com.lj.crud_supabase.presentation.feature.addproduct.AddProductScreen
import com.lj.crud_supabase.presentation.feature.auth.signin.SignInScreen
import com.lj.crud_supabase.presentation.feature.auth.signup.SignUpScreen
import com.lj.crud_supabase.presentation.feature.productdetail.ProductDetailsScreen
import com.lj.crud_supabase.presentation.feature.productlist.ProductListScreen
import com.lj.crud_supabase.ui.theme.CrudsupabaseTheme
import com.lj.crud_supabase.ui.theme.ManageProductsTheme
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.SupabaseClient
import jakarta.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var supabaseClient: SupabaseClient

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Connected Supabase", "Supabase client: $supabaseClient")
        setContent {
            ManageProductsTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                val currentBackStack by navController.currentBackStackEntryAsState()
                val currentDestination = currentBackStack?.destination
                Scaffold { innerPadding ->
                    NavHost(
                        navController,
                        startDestination = ProductListDestination.route,
                        Modifier.padding(innerPadding)
                    ) {
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
                }
            }
        }
    }
}


/*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CrudsupabaseTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CrudsupabaseTheme {
        Greeting("Android")
    }
}*/
