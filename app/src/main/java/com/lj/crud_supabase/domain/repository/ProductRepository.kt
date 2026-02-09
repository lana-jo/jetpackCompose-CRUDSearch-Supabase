package com.lj.crud_supabase.domain.repository

import com.lj.crud_supabase.data.dto.Product

interface ProductRepository {
    suspend fun createProduct(product: Product): Boolean
}
