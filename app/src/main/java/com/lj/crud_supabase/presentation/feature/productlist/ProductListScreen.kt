package com.lj.crud_supabase.presentation.feature.productlist

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.room.Delete
import com.lj.crud_supabase.R
import com.lj.crud_supabase.domain.model.AuthState
import com.lj.crud_supabase.domain.model.Product
import com.lj.crud_supabase.presentation.navigation.AddProductDestination
import com.lj.crud_supabase.presentation.navigation.AuthenticationDestination
import com.lj.crud_supabase.presentation.navigation.ProductDetailsDestination
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ProductListViewModel = hiltViewModel(),
) {
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

    Scaffold(
        topBar = {
            ModernTopAppBar(authState = authState,
                onSignIn = { navController.navigate(AuthenticationDestination.route) },
                onSignOut = { viewModel.signOut() })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(AddProductDestination.route) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Product")
            }
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
                SearchBar(query = searchQuery, onQueryChange = viewModel::onSearchQueryChange)

                if (productList.isNotEmpty()) {
                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(items = productList, key = { it.id }) { product ->

                            SwipeToDeleteItem(
                                product = product,
                                onDeleteConfirmed = { deletedProduct ->
                                    viewModel.removeItem(deletedProduct)
                                }
                            ) {
                                ProductListItem(
                                    product = product,
                                    onClick = {
                                        navController.navigate(
                                            ProductDetailsDestination.createRouteWithParam(product.id)
                                        )
                                    }
                                )
                            }

                            /*ProductListItem(product = product, onClick = {
                                navController.navigate(
                                    ProductDetailsDestination.createRouteWithParam(product.id)
                                )
                            })*/
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernTopAppBar(
    authState: AuthState,
    onSignIn: () -> Unit,
    onSignOut: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.product_list_text_screen_title),
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
            )
        },
        actions = {
            when (authState) {
                AuthState.Authenticated -> {
                    TextButton(onClick = onSignOut) {
                        Text("Sign Out", color = MaterialTheme.colorScheme.primary)
                    }
                }
                AuthState.Unauthenticated -> {
                    TextButton(onClick = onSignIn) {
                        Text("Sign In", color = MaterialTheme.colorScheme.primary)
                    }
                }
                AuthState.Initializing -> {
                    // You can show a loading indicator here if needed
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = { Text(text = "Search products...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
        shape = RoundedCornerShape(24.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListItem(product: Product, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer)
            ) {
                // You can load the product image here
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDeleteItem(
    product: Product,
    onDeleteConfirmed: (Product) -> Unit,
    content: @Composable () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                showDialog = true
                false
            } else {
                false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        enableDismissFromEndToStart = true,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.error)
                    .padding(horizontal = 24.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.onError
                )
            }
        },
        content = { content() }
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
                scope.launch {
                    dismissState.reset()
                }
            },
            icon = {
                Icon(Icons.Default.Warning, contentDescription = null)
            },
            title = { Text("Delete Product?") },
            text = {
                Text("Are you sure you want to delete \"${product.name}\"?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        onDeleteConfirmed(product)
                    }
                ) {
                    Text(
                        "Delete",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        scope.launch {
                            dismissState.reset()
                        }
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

private fun AuthState.extractAuthState() = when (this) {
    AuthState.Authenticated -> "Authenticated"
    AuthState.Initializing -> "Initializing"
    AuthState.Unauthenticated -> "Unauthenticated"
}