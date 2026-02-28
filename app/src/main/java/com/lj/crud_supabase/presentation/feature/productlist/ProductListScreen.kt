package com.lj.crud_supabase.presentation.feature.productlist

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.lj.crud_supabase.domain.models.AuthState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.lj.crud_supabase.presentation.component.MainAppScaffold
import com.lj.crud_supabase.presentation.component.SearchBar
import com.lj.crud_supabase.presentation.component.SwipeToDeleteItem
import com.lj.crud_supabase.presentation.navigation.Destination

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ProductListViewModel = hiltViewModel(),
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val isLoading by viewModel.isLoading.collectAsState(initial = false)
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.getProducts()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val authState by viewModel.authState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val productList by viewModel.productList.collectAsStateWithLifecycle(initialValue = emptyList())
    MainAppScaffold(
        currentRoute = Destination.ProductListDestination.route,
        authState = authState,
        onNavigate = { route ->
            navController.navigate(route)
        },
        onSignIn = {
            navController.navigate(Destination.SigninDestination.route)
        },
        onSignOut = {
            viewModel.signOut()
        }
    ) { padding ->
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = { viewModel.getProducts() },
            modifier = Modifier.padding(padding)
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                // Search Bar
//                SearchBar(query = searchQuery, onQueryChange = viewModel::onSearchQueryChange)
                SearchBar(
                    query = searchQuery,
                    onQueryChange = viewModel::onSearchQueryChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    placeholder = "Search products..."
                )
                if (productList.isNotEmpty()) {
                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(items = productList, key = { it.id }) { product ->
                            SwipeToDeleteItem(
                                item = product,
                                enableDialog = false,
                                onDeleteConfirmed = { viewModel.removeItem(it) }
                            ) {
                                ProductListItem(
                                    product = product,
                                    onClick = {
                                        navController.navigate(
                                            Destination.ProductDetails.createRoute(product.id)
                                        )
                                    }
                                )
                            }
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Product list is empty!", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}


private fun AuthState.extractAuthState() = when (this) {
    AuthState.Authenticated -> "Authenticated"
    AuthState.Initializing -> "Initializing"
    AuthState.Unauthenticated -> "Unauthenticated"
}
