package com.lj.crud_supabase.data.repository

import com.lj.crud_supabase.data.network.dto.ProductDto
import com.lj.crud_supabase.domain.models.Product
import com.lj.crud_supabase.domain.models.Sale

/**
 * Mendefinisikan kontrak untuk repositori yang mengelola operasi terkait produk.
 */
interface ProductRepository {

    suspend fun searchProducts(query: String): List<Product>

    /**
     * Membuat produk baru.
     *
     * @param product Model produk yang akan dibuat.
     * @return `true` jika produk berhasil dibuat, `false` jika tidak.
     */
    suspend fun createProduct(product: Product): Boolean

    /**
     * Mengambil daftar semua produk.
     *
     * @return Daftar [ProductDto] atau `null` jika terjadi kesalahan.
     */
    suspend fun getProducts(): List<ProductDto>?

    /**
     * Mengambil detail produk berdasarkan ID-nya.
     *
     * @param id ID produk yang akan diambil.
     * @return [ProductDto] yang berisi detail produk.
     */
    suspend fun getProduct(id: String): ProductDto

    /**
     * Menghapus produk berdasarkan ID-nya.
     *
     * @param id ID produk yang akan dihapus.
     */
    suspend fun deleteProduct(id: String)


    suspend fun insertSale(sale: Sale)
    suspend fun updateStock(id: String, quantitySold: Int)
    /**
     * Memperbarui produk yang sudah ada.
     *
     * @param id ID produk yang akan diperbarui.
     * @param name Nama baru produk.
     * @param price Harga baru produk.
     * @param imageName Nama file gambar baru.
     * @param imageFile Data byte dari file gambar baru.
     */
    suspend fun updateProduct(
        id: String, name: String, price: Double, imageName: String, imageFile: ByteArray
    )
}