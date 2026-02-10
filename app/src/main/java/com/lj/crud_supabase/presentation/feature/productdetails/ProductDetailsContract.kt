package com.lj.crud_supabase.presentation.feature.productdetails

import com.lj.crud_supabase.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductDetailsContract {
    val product: Flow<Product?>
    val name: Flow<String>
    val price: Flow<Double>
    val imageUrl: Flow<String>

    fun onSaveProduct(image: ByteArray)
    fun onImageChange(url: String)
}