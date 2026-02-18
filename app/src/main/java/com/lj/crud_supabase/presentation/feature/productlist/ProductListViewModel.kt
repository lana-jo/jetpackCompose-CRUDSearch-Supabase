package com.lj.crud_supabase.presentation.feature.productlist

import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lj.crud_supabase.data.repository.AuthenticationRepository
import com.lj.crud_supabase.di.SupabaseModule
import com.lj.crud_supabase.di.SupabaseModule.provideSupabaseStorage
import com.lj.crud_supabase.domain.model.AuthState
import com.lj.crud_supabase.domain.model.Product
import com.lj.crud_supabase.domain.usecase.DeleteProductUseCase
import com.lj.crud_supabase.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val authRepository: AuthenticationRepository
) : ViewModel(), ProductListContract {

    val authState: StateFlow<AuthState> = authRepository.authState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = AuthState.Initializing
    )

    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())

    private val _searchQuery = MutableStateFlow("")
    override val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    @OptIn(FlowPreview::class)
    override val productList: StateFlow<List<Product>> = searchQuery
        .debounce(500L)
        .combine(_allProducts) { query, products ->
            if (query.isBlank()) {
                products
            } else {
                products.filter {
                    it.name.contains(query, ignoreCase = true)
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _allProducts.value
        )

    private val _isLoading = MutableStateFlow(false)
    val isLoading: Flow<Boolean> = _isLoading

    init {
        getProducts()
    }

    override fun getProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = getProductsUseCase.execute(input = Unit)) {
                is GetProductsUseCase.Output.Success -> {
                    _allProducts.value = result.data
                }

                is GetProductsUseCase.Output.Failure -> {
                    // Handle failure
                }
            }
            _isLoading.value = false
        }
    }

    fun readFile(byteArray: ByteArray){
        viewModelScope.launch {

        }
    }
    override fun removeItem(product: Product) {
        viewModelScope.launch {
            // Optimistically update the UI
            val newList = _allProducts.value.toMutableList().apply {
                remove(product)
            }
            _allProducts.value = newList

            // Call api to remove
            deleteProductUseCase.execute(DeleteProductUseCase.Input(productId = product.id))
        }
    }

    override fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
        }
    }

    override fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }
    // Example – put this in your ViewModel or wherever you generate the URL
    suspend fun getImageUrl(bucket: String, path: String): String? {
        return try {
            val cleanPath = path.trim().removePrefix("/").removeSuffix("/")  // remove bad slashes
            Timber.tag("SupabaseImg")
                .d("Bucket = $bucket | Raw path = $path | Clean path = $cleanPath")

            val url = provideSupabaseStorage(
                client = SupabaseModule.provideSupabaseClient()
            )
                .from(bucket)
                .publicUrl(cleanPath)   // ← or .getPublicUrl() if your version uses that name

            Timber.tag("SupabaseImg").d("Generated public URL → $url")
            url
        } catch (e: Exception) {
            Log.e("SupabaseImg", "Failed to get public URL", e)
            e.printStackTrace()
            null   // or throw if you want
        }
    }
}