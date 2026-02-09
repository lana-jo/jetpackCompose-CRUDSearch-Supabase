package com.lj.crud_supabase.presentation.feature.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lj.crud_supabase.data.dto.Product
import com.lj.crud_supabase.data.dto.ProductDto
import com.lj.crud_supabase.data.repository.product.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productRepository: ProductRepository,
) : ViewModel() {

  /*  val authState: StateFlow<AuthState> = authRepository.authState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = AuthState.Initializing
    )*/

    private val _productList = MutableStateFlow<List<Product>?>(listOf())
    val productList: Flow<List<Product>?> = _productList


    private val _isLoading = MutableStateFlow(false)
    val isLoading: Flow<Boolean> = _isLoading

    init {
        getProducts()
    }

    fun getProducts() {
        viewModelScope.launch {
            val products = productRepository.getProducts()
            _productList.emit(products?.map { it -> it.asDomainModel() })
        }
    }

    fun removeItem(product: Product) {
        viewModelScope.launch {
            val newList = mutableListOf<Product>().apply { _productList.value?.let { addAll(it) } }
            newList.remove(product)
            _productList.emit(newList.toList())
            // Call api to remove
            productRepository.deleteProduct(id = product.id!!)
            // Then fetch again
            getProducts()
        }
    }

    private fun ProductDto.asDomainModel(): Product {
        return Product(
            id = this.id,
            name = this.name,
            price = this.price,
            image = this.image
        )
    }

}