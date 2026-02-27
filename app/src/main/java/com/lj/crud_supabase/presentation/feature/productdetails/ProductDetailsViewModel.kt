package com.lj.crud_supabase.presentation.feature.productdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lj.crud_supabase.domain.model.Product
import com.lj.crud_supabase.domain.usecase.GetProductDetailsUseCase
import com.lj.crud_supabase.domain.usecase.UpdateProductUseCase
import com.lj.crud_supabase.presentation.navigation.ProductDetailsDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * ViewModel untuk layar Detail Produk.
 *
 * ViewModel ini bertanggung jawab untuk mengambil, memperbarui, dan mengelola status UI untuk detail produk.
 *
 * @property getProductDetailsUseCase Kasus penggunaan untuk mengambil detail produk.
 * @property updateProductUseCase Kasus penggunaan untuk memperbarui produk.
 * @property savedStateHandle Handel ke status tersimpan, digunakan untuk mengambil ID produk dari argumen navigasi.
 */
@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val getProductDetailsUseCase: GetProductDetailsUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    savedStateHandle: SavedStateHandle,

    ) : ViewModel(), ProductDetailsContract {
    private val _product = MutableStateFlow<Product?>(null)
    override val product: Flow<Product?> = _product

    private val _name = MutableStateFlow("")
    override val name: Flow<String> = _name

    private val _price = MutableStateFlow(0.0)
    override val price: Flow<Double> = _price

    private val _imageUrl = MutableStateFlow("")
    override val imageUrl: Flow<String> = _imageUrl

    init {
        val productId = savedStateHandle.get<String>(ProductDetailsDestination.productId)
        productId?.let {
            getProduct(productId = it)
        }
    }

    /**
     * Mengambil detail produk berdasarkan ID produk yang diberikan.
     *
     * @param productId ID produk yang akan diambil.
     */
    private fun getProduct(productId: String) {
        viewModelScope.launch {
            val result = getProductDetailsUseCase.execute(
                GetProductDetailsUseCase.Input(
                    id = productId
                )
            )
            when (result) {

                is GetProductDetailsUseCase.Output.Success -> {
                    _product.emit(result.data)
                    _name.emit(result.data.name)
                    _price.emit(result.data.price)
                    _imageUrl.emit(result.data.image)
                    Timber.tag("GetImg").d("Image URL = ${result.data.image}")
                }
                is GetProductDetailsUseCase.Output.Failure -> {

                }
            }
        }
    }

    /**
     * Dipanggil saat nama produk diubah di UI.
     *
     * @param name Nama produk yang baru.
     */
    fun onNameChange(name: String) {
        _name.value = name
    }

    /**
     * Dipanggil saat harga produk diubah di UI.
     *
     * @param price Harga produk yang baru.
     */
    fun onPriceChange(price: Double) {
        _price.value = price
    }

    /**
     * Menyimpan perubahan pada produk, termasuk gambar yang diperbarui.
     *
     * @param image Data byte dari gambar baru.
     */
    override fun onSaveProduct(image: ByteArray) {
        viewModelScope.launch {
            if (_product.value?.image !== null) {
                updateProductUseCase.execute(
                    UpdateProductUseCase.Input(
                        id = _product.value?.id ?: "",
                        price = _price.value,
                        name = _name.value,
                        imageFile = image,
                        imageName = "image_${_product.value?.id}",

                    )

                )
                Timber.tag("UpdateImg").d("Image URL = ${_imageUrl.value}")

            }
        }
    }

    /**
     * Dipanggil saat URL gambar diubah.
     *
     * @param url URL gambar yang baru.
     */
    override fun onImageChange(url: String) {
        _imageUrl.value = url
    }
}