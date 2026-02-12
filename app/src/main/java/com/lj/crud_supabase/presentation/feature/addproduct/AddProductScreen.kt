package com.lj.crud_supabase.presentation.feature.addproduct

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lj.crud_supabase.R
import com.lj.crud_supabase.domain.usecase.CreateProductUseCase
import com.lj.crud_supabase.presentation.feature.addproduct.composables.FailScreen
import com.lj.crud_supabase.presentation.feature.addproduct.composables.LoadingScreen
import com.lj.crud_supabase.presentation.feature.addproduct.composables.SuccessScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: AddProductViewModel = hiltViewModel(),
) {
    val navigateAddProductSuccess by viewModel.navigateAddProductSuccess.collectAsState(initial = null)
    val isLoading by viewModel.isLoading.collectAsState(initial = false)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.add_product_text_screen_title),
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                )
            )
        }
    ) { padding ->
        Box(
            modifier = modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            if (isLoading == true) {
                LoadingScreen(message = "Adding Product", onCancelSelected = { navController.navigateUp() })
            } else {
                when (val result = navigateAddProductSuccess) {
                    null -> {
                        AddProductForm(navController = navController, viewModel = viewModel)
                    }
                    is CreateProductUseCase.Output.Success -> {
                        SuccessScreen(
                            message = "Product added successfully",
                            onMoreAction = { viewModel.onAddMoreProductSelected() },
                            onNavigateBack = { navController.navigateUp() }
                        )
                    }
                    is CreateProductUseCase.Output.Failure -> {
                        FailScreen(
                            message = "Failed to Add Product",
                            reason = handleError(result),
                            onRetrySelected = { viewModel.onRetrySelected() },
                            onNavigateBack = { navController.navigateUp() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AddProductForm(navController: NavController, viewModel: AddProductViewModel) {
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            label = { Text("Product name") },
            modifier = Modifier.fillMaxWidth(),
            value = name,
            onValueChange = { name = it },
            shape = RoundedCornerShape(16.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(
            label = { Text("Product price") },
            modifier = Modifier.fillMaxWidth(),
            value = price,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = { price = it },
            shape = RoundedCornerShape(16.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.onCreateProduct(
                    name = name,
                    price = price.toDoubleOrNull() ?: 0.0,
                )
            },
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = "Add Product", modifier = Modifier.padding(8.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { navController.navigateUp() },
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = "Cancel", modifier = Modifier.padding(8.dp))
        }
    }
}

private fun handleError(failResult: CreateProductUseCase.Output.Failure?): String {
    return when (failResult) {
        is CreateProductUseCase.Output.Failure.InternalError -> "Internal Error"
        is CreateProductUseCase.Output.Failure.BadRequest -> "Bad request"
        is CreateProductUseCase.Output.Failure.Conflict -> "Conflict"
        is CreateProductUseCase.Output.Failure.Unauthorized -> "Unauthorized"
        else -> "An unexpected error occurred"
    }
}