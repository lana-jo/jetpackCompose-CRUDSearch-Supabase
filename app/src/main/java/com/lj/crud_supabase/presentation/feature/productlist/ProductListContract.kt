package com.lj.crud_supabase.presentation.feature.productlist

import com.lj.crud_supabase.domain.model.Product
import kotlinx.coroutines.flow.Flow

/**
 * Mendefinisikan kontrak antara View dan ViewModel untuk layar daftar produk.
 */
interface ProductListContract {

    /**
     * Aliran (Flow) yang memancarkan daftar produk.
     * Akan memancarkan `null` jika terjadi kesalahan.
     */
    val productList: Flow<List<Product>?>

    /**
     * Menghapus item produk dari daftar.
     *
     * @param product Produk yang akan dihapus.
     */
    fun removeItem(product: Product)

    /**
     * Memuat atau memuat ulang daftar produk.
     */
    fun getProducts()

    /**
     * Mengeluarkan pengguna dari sesi saat ini.
     */
    fun signOut()
}