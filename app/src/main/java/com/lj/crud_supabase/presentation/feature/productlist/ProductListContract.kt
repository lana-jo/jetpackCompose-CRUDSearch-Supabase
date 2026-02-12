package com.lj.crud_supabase.presentation.feature.productlist

import com.lj.crud_supabase.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * Mendefinisikan kontrak antara View dan ViewModel untuk layar daftar produk.
 */
interface ProductListContract {

    /**
     * Aliran (Flow) yang memancarkan daftar produk.
     */
    val productList: Flow<List<Product>>

    /**
     * Aliran (StateFlow) yang menyimpan query pencarian saat ini.
     */
    val searchQuery: StateFlow<String>

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

    /**
     * Dipanggil ketika query pencarian berubah.
     *
     * @param query Query pencarian yang baru.
     */
    fun onSearchQueryChange(query: String)
}