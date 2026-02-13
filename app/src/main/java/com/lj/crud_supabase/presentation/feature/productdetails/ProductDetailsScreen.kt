package com.lj.crud_supabase.presentation.feature.productdetails

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.rememberAsyncImagePainter
import com.lj.crud_supabase.R
import com.lj.crud_supabase.presentation.utils.formatPriceToIDR
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.InputStream

@OptIn(ExperimentalCoilApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProductDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductDetailsViewModel = hiltViewModel(),
    navController: NavController,
    productId: String?,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val name by viewModel.name.collectAsState(initial = "")
    val price by viewModel.price.collectAsState(initial = 0.0)
    val imageUrl by viewModel.imageUrl.collectAsState(initial = "")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        text = stringResource(R.string.product_details_text_screen_title),
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
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { padding ->
        val contentResolver = LocalContext.current.contentResolver
        val imageUri = remember { mutableStateOf<Uri?>(null) }

        val galleryLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.GetContent())
            { uri ->
                uri?.let {
                    imageUri.value = it
                    viewModel.onImageChange(it.toString())
                }
            }

        Column(
            modifier = modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.BottomEnd) {
                Image(
                    painter = rememberAsyncImagePainter(model = imageUri.value ?: imageUrl),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
                IconButton(
                    onClick = { galleryLauncher.launch("image/*") },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit Image",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                label = { Text("Product name") },
                modifier = Modifier.fillMaxWidth(),
                value = name,
                onValueChange = { viewModel.onNameChange(it) },
                shape = RoundedCornerShape(16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            /*OutlinedTextField(
                label = { Text("Product price") },
                modifier = Modifier.fillMaxWidth(),
                value = price.toString(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = { newValue ->
                    newValue.toDoubleOrNull()?.let { viewModel.onPriceChange(it) }
                },
                shape = RoundedCornerShape(16.dp)
            )*/
            OutlinedTextField(
                label = { Text("Product price (Rp)") },
                modifier = Modifier.fillMaxWidth(),
                value = price.toString(),  // Tetap number untuk input
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = { newValue ->
                    newValue.toDoubleOrNull()?.let { viewModel.onPriceChange(it) }
                },
                shape = RoundedCornerShape(16.dp),
                suffix = {
                    if (price > 0) {
                        Text(
                            text = formatPriceToIDR(price),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    val imageByteArray = imageUri.value?.let { uriToByteArray(contentResolver, it) } ?: byteArrayOf()
                    viewModel.onSaveProduct(image = imageByteArray)
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar("Product updated successfully!")
                        navController.navigateUp()
                    }
                },
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "Save changes", modifier = Modifier.padding(8.dp))
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
}

private fun uriToByteArray(contentResolver: ContentResolver, uri: Uri): ByteArray {
    return contentResolver.openInputStream(uri)?.use { it.readBytes() } ?: byteArrayOf()
}
