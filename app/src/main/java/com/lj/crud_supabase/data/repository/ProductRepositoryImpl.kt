package com.lj.crud_supabase.data.repository

import com.lj.crud_supabase.data.dto.Product
import com.lj.crud_supabase.domain.repository.ProductRepository
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(private val postgrest: Postgrest) : ProductRepository {
    override suspend fun createProduct(product: Product): Boolean {
        return try {
            postgrest.from("products").insert(product)
            true
        } catch (e: Exception) {
            false
        }
    }
}
