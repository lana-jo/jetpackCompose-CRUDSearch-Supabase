/*
package com.lj.crud_supabase




//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.lj.crud_supabase.navigate.AddProductDestination
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.lj.crud_supabase.navigation.AddProductDestination
import com.lj.crud_supabase.navigation.ProductDetailsDestination
import com.lj.crud_supabase.ui.ProductListItem
import com.lj.crud_supabase.presentation.feature.productlist.ProductListViewModel




//import com.lj.crud_supabase.domain.model.AuthState
//import com.lj.crud_supabase.presentation.navigation.AddProductDestination
//import com.lj.crud_supabase.presentation.navigation.AuthenticationDestination
//import com.lj.crud_supabase.presentation.navigation.ProductDetailsDestination




@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductListItem(product: Product, modifier: Modifier, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            product.image?.let { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = product.name,
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

fun Card(
    onClick: () -> Unit,
    modifier: Modifier,
    elevation: CardElevation,
    content: () -> Unit
) {
}



@Composable
fun AsyncImage(model: String, contentDescription: String, modifier: Modifier) {
    TODO("Not yet implemented")
}

@Composable
fun AsyncImage(model: String, contentDescription: String, modifier: Modifier) {
    TODO("Not yet implemented")
}




@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ProductListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ProductListViewModel = hiltViewModel(),
) {
    val isLoading by viewModel.isLoading.collectAsState(initial = false)
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    // If `lifecycleOwner` changes, dispose and reset the effect
    DisposableEffect(lifecycleOwner) {
        // Create an observer that triggers our remembered callbacks
        // for sending analytics events
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.getProducts()
            }
        }
        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    val authState = viewModel.authState.collectAsStateWithLifecycle()
    SwipeRefresh(state = swipeRefreshState, onRefresh = { viewModel.getProducts() }) {
        Scaffold(
            topBar = {
                TopAppBar(
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    title = {
                        Text(
                            text = stringResource(R.string.product_list_text_screen_title) + " - " + authState.value.extractAuthState(),
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                    },
                    actions = {
                        Button(onClick = {
                            viewModel.signOut()
                        }) {
                            Text(
                                text = "Sign out",
                                color = MaterialTheme.colorScheme.onPrimary,
                            )
                        }
                    }
                )
            },
            floatingActionButton = {
                AddProductButton(onClick = { navController.navigate(AddProductDestination.route) })
            }
        ) { padding ->
            Column(modifier = modifier.padding(paddingValues = padding)) {
                androidx.compose.material3.Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    onClick = {
                        navController.navigate(AuthenticationDestination.route)
                    }) {
                    Text("Authentication feature")
                }

                val productList = viewModel.productList.collectAsState(initial = listOf()).value
                if (!productList.isNullOrEmpty()) {
                    LazyColumn(
                        modifier = modifier.padding(padding),
                        contentPadding = PaddingValues(5.dp)
                    ) {
                        itemsIndexed(
                            items = productList,
                            key = { _, product -> product.name }) { _, item ->
                            val state = rememberDismissState(
                                confirmStateChange = {
                                    if (it == DismissValue.DismissedToStart) {
                                        // Handle item removed
                                        viewModel.removeItem(item)
                                    }
                                    true
                                }
                            )
                            SwipeToDismiss(
                                state = state,
                                background = {
                                    val color by animateColorAsState(
                                        targetValue = when (state.dismissDirection) {
                                            DismissDirection.StartToEnd -> MaterialTheme.colorScheme.primary
                                            DismissDirection.EndToStart -> MaterialTheme.colorScheme.primary.copy(
                                                alpha = 0.2f
                                            )

                                            null -> Color.Transparent
                                        }
                                    )
                                    Box(
                                        modifier = modifier
                                            .fillMaxSize()
                                            .background(color = color)
                                            .padding(16.dp),
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Delete,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = modifier.align(Alignment.CenterEnd)
                                        )
                                    }

                                },
                                dismissContent = {
                                    ProductListItem(
                                        product = item,
                                        modifier = modifier,
                                        onClick = {
                                            navController.navigate(
                                                ProductDetailsDestination.createRouteWithParam(
                                                    item.id
                                                )
                                            )
                                        },
                                    )
                                },
                                directions = setOf(DismissDirection.EndToStart),
                            )
                        }
                    }
                } else {
                    Text("Product list is empty!")
                }
            }


        }
    }
}

@Composable
private fun AddProductButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = null,
        )
    }
}

private fun AuthState.extractAuthState() = when (this) {
    AuthState.Authenticated -> "Authenticated"
    AuthState.Initializing -> "Initializing"
    AuthState.Unauthenticated -> "Unauthenticated"
}







@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
//@Preview(showBackground = true)
fun ProductListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ProductListViewModel = hiltViewModel(),
) {
    val isLoading by viewModel.isLoading.collectAsState(initial = false)
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
    SwipeRefresh(state = swipeRefreshState, onRefresh = { viewModel.getProducts() }) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.product_list_text_screen_title),
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                    },
                )
            },
            floatingActionButton = {
                AddProductButton(onClick = { navController.navigate(AddProductDestination.route) })
            }
        ) { padding ->
            val productList = viewModel.productList.collectAsState(initial = listOf()).value
            if (productList.isNotEmpty()) {
                LazyColumn(
                    modifier = modifier.padding(padding),
                    contentPadding = PaddingValues(5.dp)
                ) {
                    itemsIndexed(
                        items = productList,
                        key = { _, product -> product.name }) { _, item ->
                        val state = rememberDismissState(
                            confirmValueChange = {
                                if (it == DismissValue.DismissedToStart) {
                                    // Handle item removed
                                    viewModel.removeItem(item)
                                }
                                true
                            }
                        )
                        SwipeToDismissBox(
                            state = state,
                            backgroundContent = {
                                val color by animateColorAsState(
                                    targetValue = when (state.dismissDirection) {
                                        DismissDirection.StartToEnd -> MaterialTheme.colorScheme.primary
                                        DismissDirection.EndToStart -> MaterialTheme.colorScheme.primary.copy(
                                            alpha = 0.2f
                                        )
                                        null -> Color.Transparent
                                    },
                                    label = ""
                                )
                                Box(
                                    modifier = modifier
                                        .fillMaxSize()
                                        .background(color = color)
                                        .padding(16.dp),
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Delete,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = modifier.align(Alignment.CenterEnd)
                                    )
                                }

                            },
                            content = {
                                ProductListItem(
                                    product = item,
                                    modifier = modifier,
                                    onClick = {
                                        navController.navigate(
                                            ProductDetailsDestination.createRouteWithParam(
                                                item.id
                                            )
                                        )
                                    },
                                )
                            },
                            enableDismissFromEndToStart = true,
                            enableDismissFromStartToEnd = false
                        )
                    }
                }
            } else {
                Text("Product list is empty!")
            }

        }
    }
}

@Composable
fun SwipeRefresh(
    state: rememberSwipeRefreshState,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    TODO("Not yet implemented")
}

@Composable
private fun AddProductButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = null,
        )
    }
}

*/
