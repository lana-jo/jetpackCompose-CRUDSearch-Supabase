package com.lj.crud_supabase.presentation.feature.productlist

import com.lj.crud_supabase.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductListContract {
    val productList: Flow<List<Product>?>
    fun removeItem(product: Product)
    fun getProducts()
    fun signOut()
}