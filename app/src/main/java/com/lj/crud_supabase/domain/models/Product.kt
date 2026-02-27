package com.lj.crud_supabase.domain.models

data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val image: String = ""
)
