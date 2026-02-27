package com.lj.crud_supabase.domain.models

data class CategoryModel(
    var id: String,
    var name: String? = null,
    var image: String? = null
)
