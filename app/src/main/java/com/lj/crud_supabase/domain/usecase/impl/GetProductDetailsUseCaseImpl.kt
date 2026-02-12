package com.lj.crud_supabase.domain.usecase.impl

import com.lj.crud_supabase.data.repository.ProductRepository
import com.lj.crud_supabase.domain.model.Product
import com.lj.crud_supabase.domain.usecase.GetProductDetailsUseCase
import javax.inject.Inject

/**
 * Implementasi dari [GetProductDetailsUseCase] yang mengambil detail produk dari [ProductRepository].
 *
 * @property productRepository Repositori untuk mengambil data produk.
 */
class GetProductDetailsUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository,
) : GetProductDetailsUseCase {

    /**
     * Menjalankan kasus penggunaan untuk mengambil detail produk.
     *
     * @param input Input yang berisi ID produk yang akan diambil.
     * @return [GetProductDetailsUseCase.Output] yang berisi detail produk jika berhasil.
     */
    override suspend fun execute(input: GetProductDetailsUseCase.Input): GetProductDetailsUseCase.Output {
        val result = productRepository.getProduct(input.id)
        return GetProductDetailsUseCase.Output.Success(
            data = Product(
                id = result.id ?: "",
                name = result.name,
                price = result.price,
                image = result.image ?: "",
            )
        )
    }
}