package com.lj.crud_supabase.presentation.feature.productdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lj.crud_supabase.data.dto.Product
import com.lj.crud_supabase.data.dto.ProductDto
import com.lj.crud_supabase.data.repository.product.ProductRepository
import com.lj.crud_supabase.navigate.ProductDetailsDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel untuk layar detail produk.
 * Mengelola state dan logika bisnis yang terkait dengan detail produk.
 *
 * @param productRepository Repositori untuk mengakses data produk.
 * @param savedStateHandle Menangani state yang disimpan, termasuk argumen navigasi.
 */
@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    // StateFlow untuk menyimpan detail produk saat ini.
    private val _product = MutableStateFlow<Product?>(null)
    val product: Flow<Product?> = _product

    // StateFlow untuk menyimpan nama produk yang dapat diedit.
    private val _name = MutableStateFlow("")
    val name: Flow<String> = _name

    // StateFlow untuk menyimpan harga produk yang dapat diedit.
    private val _price = MutableStateFlow(0.0)
    val price: Flow<Double> = _price

    // StateFlow untuk menyimpan URL gambar produk.
    private val _imageUrl = MutableStateFlow("")
    val imageUrl: Flow<String> = _imageUrl

    init {
        // Mengambil ID produk dari argumen navigasi saat ViewModel dibuat.
        val productId = savedStateHandle.get<String>(ProductDetailsDestination.productId)
        productId?.let {
            // Memuat detail produk jika ID tersedia.
            getProduct(productId = it)
        }
    }

    /**
     * Mengambil detail produk dari repositori berdasarkan ID-nya.
     * @param productId ID dari produk yang akan diambil.
     */
    private fun getProduct(productId: String) {
        viewModelScope.launch {
            val result = productRepository.getProduct(productId).asDomainModel()
            // Memperbarui StateFlow dengan data produk yang telah diambil.
            _product.emit(result)
            _name.emit(result.name)
            _price.emit(result.price)
        }
    }

    /**
     * Dipanggil saat nilai nama produk diubah di UI.
     * @param name Nama produk yang baru.
     */
    fun onNameChange(name: String) {
        _name.value = name
    }

    /**
     * Dipanggil saat nilai harga produk diubah di UI.
     * @param price Harga produk yang baru.
     */
    fun onPriceChange(price: Double) {
        _price.value = price
    }

    /**
     * Menyimpan (memperbarui) produk dengan data yang telah diubah.
     * @param image Data gambar dalam bentuk ByteArray.
     */
    fun onSaveProduct(image: ByteArray) {
        viewModelScope.launch {
            productRepository.updateProduct(
                id = _product.value?.id,
                price = _price.value,
                name = _name.value,
                imageFile = image,
                imageName = "image_${_product.value?.id}",
            )
        }
    }

    /**
     * Dipanggil saat URL gambar diubah.
     * @param url URL gambar yang baru.
     */
    fun onImageChange(url: String) {
        _imageUrl.value = url
    }

    /**
     * Fungsi ekstensi untuk mengubah objek ProductDto menjadi objek domain Product.
     */
    private fun ProductDto.asDomainModel(): Product {
        return Product(
            id = this.id,
            name = this.name,
            price = this.price,
            image = this.image
        )
    }
}
