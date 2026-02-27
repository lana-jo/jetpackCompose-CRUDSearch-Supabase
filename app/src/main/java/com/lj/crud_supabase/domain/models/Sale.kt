package com.lj.crud_supabase.domain.models

data class CartItem(
    val product: Product,
    val quantity: Int = 1
)

data class Sale(
    val products: List<CartItem>,
    val total: Double,
    val createdAt: String = "" // Populated by Supabase
)