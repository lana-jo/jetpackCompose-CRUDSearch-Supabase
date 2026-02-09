package com.lj.crud_supabase.data.repository.product

import com.lj.crud_supabase.data.dto.Product
import com.lj.crud_supabase.data.dto.ProductDto

interface ProductRepository {
    suspend fun createProduct(product: Product): Boolean
    suspend fun getProducts(): List<ProductDto>?
    suspend fun getProduct(id: String): ProductDto
    suspend fun deleteProduct(id: String)
    suspend fun updateProduct(
        id: String?, name: String, price: Double, imageName: String, imageFile: ByteArray
    )
}