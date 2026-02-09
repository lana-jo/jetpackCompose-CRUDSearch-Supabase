package com.lj.crud_supabase.data.dto

//import com.google.gson.annotations.SerializedName

/*data object
        Product*/
//@Serializable
data class Product(
    val id: String?,
    val name: String,
    val price: Double,
    val image: String? = "",
//    @SerializedName("user_id")
    val userId: String? = null
)
/*
object Product {
}*/
