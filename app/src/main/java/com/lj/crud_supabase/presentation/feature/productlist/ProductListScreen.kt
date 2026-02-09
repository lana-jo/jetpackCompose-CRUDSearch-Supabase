package com.lj.crud_supabase.presentation.feature.productlist

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.lj.crud_supabase.R
import com.lj.crud_supabase.navigate.AddProductDestination
import com.lj.crud_supabase.navigate.ProductDetailsDestination
import com.lj.crud_supabase.presentation.feature.productlist.ProductListViewModel

/**
 * Composable utama untuk menampilkan daftar produk.
 *
 * @param modifier Modifier untuk menyesuaikan tata letak.
 * @param navController NavController untuk navigasi antar layar.
 * @param viewModel ProductListViewModel yang disediakan oleh Hilt untuk mengelola data dan logika bisnis.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ProductListScreen(
    modifier: Modifier = Modifier.Companion,
    navController: NavController,
    viewModel: ProductListViewModel = hiltViewModel(),
) {
    // Mengamati status loading dari ViewModel
    val isLoading by viewModel.isLoading.collectAsState(initial = false)
    // State untuk mengelola aksi swipe-to-refresh
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
    // Tampilan swipe-to-refresh yang akan memanggil getProducts() di ViewModel saat di-refresh
    SwipeRefresh(state = swipeRefreshState, onRefresh = { viewModel.getProducts() }) {
        // Scaffold menyediakan struktur dasar tata letak Material Design
        Scaffold(
            topBar = {
                // TopAppBar sebagai bar bagian atas layar
                TopAppBar(
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    title = {
                        Text(
                            text = stringResource(R.string.product_list_text_screen_title),
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                    },
                )
            },
            // FloatingActionButton untuk menambahkan produk baru
            floatingActionButton = {
                AddProductButton(onClick = { navController.navigate(AddProductDestination.route) })
            }
        ) { padding ->
            // Mengamati daftar produk dari ViewModel
            val productList = viewModel.productList.collectAsState(initial = listOf()).value
            // Menampilkan daftar produk jika tidak kosong
            if (!productList.isNullOrEmpty()) {
                // LazyColumn untuk menampilkan daftar yang dapat di-scroll secara efisien
                LazyColumn(
                    modifier = modifier.padding(padding),
                    contentPadding = PaddingValues(5.dp)
                ) {
                    itemsIndexed(
                        items = productList,
                        key = { _, product -> product.name }) { _, item ->
                        // State untuk mengelola aksi swipe-to-dismiss
                        val state = rememberDismissState(
                            confirmStateChange = {
                                if (it == DismissValue.DismissedToStart) {
                                    // Menghapus item saat digeser ke awal (kiri)
                                    viewModel.removeItem(item)
                                }
                                true
                            }
                        )
                        // SwipeToDismiss untuk item dalam daftar
                        SwipeToDismiss(
                            state = state,
                            background = {
                                // Latar belakang yang muncul saat item digeser
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
                                        modifier = modifier.align(Alignment.Companion.CenterEnd)
                                    )
                                }

                            },
                            dismissContent = {
                                // Konten item produk yang sebenarnya
                                ProductListItem(
                                    product = item,
                                    modifier = modifier,
                                    onClick = {
                                        navController.navigate(
                                            ProductDetailsDestination.createRouteWithParam(
                                                item.id!!
                                            )
                                        )
                                    },
                                )
                            },
                            // Hanya mengizinkan swipe dari akhir (kanan) ke awal (kiri)
                            directions = setOf(DismissDirection.EndToStart),
                        )
                    }
                }
            } else {
                // Menampilkan pesan jika daftar produk kosong
                Text("Product list is empty!")
            }

        }
    }
}


/**
 * Composable untuk tombol FloatingActionButton.
 *
 * @param modifier Modifier untuk menyesuaikan tata letak.
 * @param onClick Aksi yang akan dijalankan saat tombol diklik.
 */
@Composable
private fun AddProductButton(
    modifier: Modifier = Modifier.Companion,
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
