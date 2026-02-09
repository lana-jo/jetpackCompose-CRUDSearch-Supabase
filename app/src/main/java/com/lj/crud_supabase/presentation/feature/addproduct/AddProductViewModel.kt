package com.lj.crud_supabase.presentation.feature.addproduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lj.crud_supabase.data.dto.Product
import com.lj.crud_supabase.data.repository.product.ProductRepository
import com.lj.crud_supabase.data.repository.user.AuthRepository
import com.lj.crud_supabase.domain.usecase.CreateProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val createProductUseCase: CreateProductUseCase
) : ViewModel(), AddProductContract {

    private val _navigateAddProductSuccess = MutableStateFlow<CreateProductUseCase.Output?>(null)
    override val navigateAddProductSuccess: Flow<CreateProductUseCase.Output?> =
        _navigateAddProductSuccess

    private val _isLoading = MutableStateFlow(false)
    override val isLoading: Flow<Boolean> = _isLoading

    private val _showSuccessMessage = MutableStateFlow(false)
    override val showSuccessMessage: Flow<Boolean> = _showSuccessMessage

    override fun onCreateProduct(name: String, price: Double) {
        if (name.isEmpty() || price <= 0) {
            _navigateAddProductSuccess.value = CreateProductUseCase.Output.Failure.BadRequest
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
            val product = Product(
                id = UUID.randomUUID().toString(),
                name = name,
                price = price,
            )
            when (val result =
                createProductUseCase.execute(CreateProductUseCase.Input(product = product))) {
                is CreateProductUseCase.Output.Success -> {
                    _isLoading.value = false
                    _showSuccessMessage.emit(true)
                    _navigateAddProductSuccess.value = result
                }
                is CreateProductUseCase.Output.Failure -> {
                    _isLoading.value = false
                    _navigateAddProductSuccess.value = result
                }
            }
          /*  val userId = authRepository.getCurrentUserId()
            if (userId == null) {
                _navigateAddProductSuccess.value = CreateProductUseCase.Output.Failure.Unauthorized
                _isLoading.value = false
                return@launch
            }

            val product = Product(
                id = UUID.randomUUID().toString(),
                name = name,
                price = price,
                image = null,
                userId = userId
            )

            val success = productRepository.createProduct(product = product)

            if (success) {
              *//*  _navigateAddProductSuccess.value = CreateProductUseCase.Output.Success
                _showSuccessMessage.value = true*//*
            } else {
                _navigateAddProductSuccess.value = CreateProductUseCase.Output.Failure.InternalError
            }
            _isLoading.value = false*/
        }
    }

    /*   override fun onCreateProduct(name: String, price: Double) {
        if (name.isEmpty() || price <= 0) return
        viewModelScope.launch {
            _isLoading.value = true
            val userId = authRepository.getCurrentUserId()
            if (userId != null) {
                val product = Product(
                    id = UUID.randomUUID().toString(),
                    name = name,
                    price = price,
                    image = null,
                    userId = userId
                )
                productRepository.createProduct(product = product)
            }
            _isLoading.value = false
            _showSuccessMessage.emit(true)
        }
    }*/

    override fun onAddMoreProductSelected() {
        _navigateAddProductSuccess.value = null
    }

    override fun onRetrySelected() {
        _navigateAddProductSuccess.value = null
    }
}
