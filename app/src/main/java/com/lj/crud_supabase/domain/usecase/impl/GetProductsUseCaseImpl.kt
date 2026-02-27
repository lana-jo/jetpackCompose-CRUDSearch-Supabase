package com.lj.crud_supabase.domain.usecase.impl

import com.lj.crud_supabase.data.repository.ProductRepository
import com.lj.crud_supabase.domain.model.Product
import com.lj.crud_supabase.domain.usecase.GetProductsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementasi dari [GetProductsUseCase] yang mengambil daftar produk dari [ProductRepository].
 *
 * @property productRepository Repositori untuk mengambil data produk.
 */
class GetProductsUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository,
) : GetProductsUseCase {

    /**
     * Menjalankan kasus penggunaan untuk mengambil daftar produk.
     *
     * @param input Unit, karena kasus penggunaan ini tidak memerlukan parameter masukan apa pun.
     * @return [GetProductsUseCase.Output] yang berisi daftar produk jika berhasil, atau [GetProductsUseCase.Output.Failure] jika terjadi kesalahan.
     */
    override suspend fun execute(input: Unit): GetProductsUseCase.Output =
        withContext(Dispatchers.IO) {
            val result = productRepository.getProducts()
            return@withContext result?.let { it ->
                GetProductsUseCase.Output.Success(data = it.map {
                    Product(
                        id = it.id ?: "",
                        name = it.name,
                        price = it.price,
                        image = it.image ?: ""
                    )
                })
            } ?: GetProductsUseCase.Output.Failure
        }
}