package com.lj.crud_supabase.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Objek Transfer Data (DTO) untuk produk.
 *
 * Kelas ini mewakili produk saat ditransfer melalui jaringan.
 * Digunakan untuk serialisasi dan deserialisasi data produk.
 *
 * @property name Nama produk.
 * @property price Harga produk.
 * @property image URL gambar produk. Bisa null atau string kosong.
 * @property id Pengidentifikasi unik produk. Bisa null jika produknya baru.
 */
@Serializable
data class ProductDto(

    @SerialName("name")
    val name: String,

    @SerialName("price")
    val price: Double,

    @SerialName("image")
    val image: String? = "",

    @SerialName("id")
    val id: String? = null,
)
