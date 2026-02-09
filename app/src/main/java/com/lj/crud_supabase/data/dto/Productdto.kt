package com.lj.crud_supabase.data.dto

import kotlinx.serialization.*

@Serializable
data class ProductDto(
    @SerialName("name")
    val name: String,
    @SerialName("price")
    val price: Double,
    @SerialName("image")
    val image: String? = "",
    @SerialName("id")
    val id: String? = null
)
/*

data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val image: String?
)*/
